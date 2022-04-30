import logging

from botbuilder.ai.luis import LuisRecognizer
from botbuilder.core import ActivityHandler, ConversationState, BotTelemetryClient, NullTelemetryClient
from botbuilder.dialogs.prompts import TextPrompt, PromptOptions

from config import DefaultConfig
from flight_booking_recognizer import FlightBookingRecognizer
from BookReservation import BookReservation
from botbuilder.dialogs import (
    WaterfallDialog,
    WaterfallStepContext,
    DialogTurnResult, DialogSet, PromptValidatorContext,
)
CONFIG = DefaultConfig()
from opencensus.ext.azure.log_exporter import AzureLogHandler
from opencensus.stats import measure as measure_module
from botbuilder.core import (
    MessageFactory,
    TurnContext,

)


class BotDialog(ActivityHandler):
    def __init__(self,
                 conversation: ConversationState,
                 luis_recognizer: FlightBookingRecognizer,
                 telemetry_client: BotTelemetryClient = None):
        self.telemetry_client = telemetry_client or NullTelemetryClient()
        self.LuisReg = luis_recognizer
        self.con_statea = conversation

        wt_dialog = WaterfallDialog("main_dialog",
                                            [self.GetFirstIntent, self.GetDestination, self.GetDepartureCity,
                                             self.GetDepartureDate,
                                             self.GetReturnDate, self.GetBudget, self.GetParticipant, self.Completed])
        wt_dialog.telemetry_client =  self.telemetry_client
        self.state_prop = self.con_statea.create_property("dialog_set")
        self.telemetry_client = telemetry_client
        self.dialog_set = DialogSet(self.state_prop)
        self.dialog_set.telemetry_client = self.telemetry_client

        self.dialog_set.add(TextPrompt("text_prompt"))
        self.dialog_set.add(TextPrompt("text_budget", self.GetBudget))
        self.reservation = BookReservation()

        self.dialog_set.add(TextPrompt("DestinationCity", self.IsDestinationCorrect))
        self.dialog_set.add(TextPrompt("DepartureCity", self.IsDepartureCityCorrect))
        self.dialog_set.add(TextPrompt("DepartureDate", self.IsDepartureDateCorrect))
        self.dialog_set.add(TextPrompt("ReturnDate", self.IsReturnDateCorrect))
        self.dialog_set.add(TextPrompt("Budget", self.IsBudgetCorrect))
        self.dialog_set.add(TextPrompt("Participant", self.IsParticipant))
        self.dialog_set.add(wt_dialog)
        self.logger = logging.getLogger(__name__)
        self.logger.addHandler(AzureLogHandler(connection_string=CONFIG.APPINSIGHTS_INSTRUMENTATION))
        self.budget_measure = measure_module.MeasureInt("budget",
                                                          "the budget is ",
                                                          "Accepted")

    async def IsDestinationCorrect(self, prompt_valid: PromptValidatorContext):
        if prompt_valid.recognized.succeeded is False:
            await prompt_valid.context.send_activity("Please enter the valid destination")
            return False
        else:
            value = str(prompt_valid.recognized.value)
            if not value in "yes":
                await prompt_valid.context.send_activity("Please enter the valid Destination")
                return False
            else:
                if value in "yes":
                    self.logger.info(f"Input City was correctly detected the City passed was: {value}")
                    return True
                self.reservation.CityDestination = value
                value = str(prompt_valid.recognized.value)
                if len(value) < 3:
                    self.logger.warning(f"Input City is invalid The wrong destination City passed was: {value}")
                    await prompt_valid.context.send_activity("Please enter the valid destination")
                    return False
                else:
                    return True

    async def IsDepartureCityCorrect(self, prompt_valid: PromptValidatorContext):
        if (prompt_valid.recognized.succeeded is False):
            await prompt_valid.context.send_activity("Please enter the valid Departure")
            return False
        else:
            value = str(prompt_valid.recognized.value)

            if not value in "yes":
                await prompt_valid.context.send_activity("Please enter the valid Departure")
                return False
            else:
                if value in "yes":
                    self.logger.info(f"Input City was correctly detected the City passed was: {value}")
                    return True
                value = str(prompt_valid.recognized.value)
                self.reservation.CityDeparture = value
                if len(value) < 3:
                    self.logger.warning(f"Input City is invalid The wrong departure City passed was: {value}")
                    await prompt_valid.context.send_activity("Please enter the valid city Departure")
                    return False
                else:
                    return True

    async def IsDepartureDateCorrect(self, prompt_valid: PromptValidatorContext):
        isValidDate = False
        if (prompt_valid.recognized.succeeded is False):
            await prompt_valid.context.send_activity("Please enter the valid date Departure dd-mm-yyyy")
            return False
        else:

            value = str(prompt_valid.recognized.value)
            isValidDate = BookReservation.CheckDate(self.reservation, value, None)

            if not value in "yes" and not isValidDate:
                await prompt_valid.context.send_activity("Please enter the valid date Departure dd-mm-yyyy ")
                return False
            else:
                self.reservation.DateDeparture = value
                value = str(prompt_valid.recognized.value)
                isValidDate = BookReservation.CheckDate(self.reservation, value, None)
                if (isValidDate):
                    return True
                else:
                    return False

    async def IsReturnDateCorrect(self, prompt_valid: PromptValidatorContext):
        if (prompt_valid.recognized.succeeded is False):
            await prompt_valid.context.send_activity("Please enter the valid Return date dd-mm-yyyy")
            return False
        else:
            value = str(prompt_valid.recognized.value)
            isValidDate = BookReservation.CheckDate(self.reservation, value, self.reservation.DateDeparture)
            if not value in "yes" and not isValidDate:
                await prompt_valid.context.send_activity("Please enter the valid Return date dd-mm-yyyy ")
                return False
            else:
                self.reservation.DateReturn = value
                value = str(prompt_valid.recognized.value)
                isValidDate = BookReservation.CheckDate(self.reservation, value, self.reservation.DateDeparture)
                if (isValidDate):
                    return True
                else:

                    return False

    async def IsBudgetCorrect(self, prompt_valid: PromptValidatorContext):
        if (prompt_valid.recognized.succeeded is False):
            await prompt_valid.context.send_activity("Please enter the valid mobile budget")
            return False
        else:
            value = str(prompt_valid.recognized.value)
            if not value in "yes" and not (
                    value.__contains__("dollar") or value.__contains__("€") or value.__contains__("$")):
                await prompt_valid.context.send_activity("Please enter the valid budget ( ex: 200€")
                return False
            else:
                self.reservation.Budget = value
                value = str(prompt_valid.recognized.value)
                if len(value) < 3:
                    self.logger.warning(f"Input Budget is invalid The wrong budget  passed was: {value}")
                    await prompt_valid.context.send_activity("Please enter the valid budget ( ex: 200€)")
                    return False
                if (value.__contains__("dollar") or value.__contains__("€") or value.__contains__(
                        "$") or value.__contains__("euro")):
                    return True

            return True

    async def IsParticipant(self, prompt_valid: PromptValidatorContext):
        if (prompt_valid.recognized.succeeded is False):
            await prompt_valid.context.send_activity("Hey please enter the number")
            return False
        else:
            value = str(prompt_valid.recognized.value)
            if not value in "yes" and not (value.isdigit()):
                await prompt_valid.context.send_activity("Please enter the valid nb of person ")
                return False
            else:
                self.reservation.NbPeople = value
                value = str(prompt_valid.recognized.value)
                if len(value) > 2:
                    self.logger.warning(f"Input nbParticipant is invalid The wrong nbParticipant  passed was: {value}")
                    await prompt_valid.context.send_activity("The max is 99")
                    return False
                if (value.isdigit() and value.isdigit()):
                    return True

            return True

    async def GetFirstIntent(self, waterfall_step: WaterfallStepContext):
        self.reservation = BookReservation()
        return await waterfall_step.prompt("text_prompt", PromptOptions(
            prompt=MessageFactory.text("Hello, where do you want to go?")))

    async def GetDestination(self, waterfall_step: WaterfallStepContext) -> DialogTurnResult:
        luis_resultv2 = await self.LuisReg.recognizev2(waterfall_step._turn_context)
        dict_luis_resultv2 = luis_resultv2.as_dict()
        self.reservation = BookReservation.FetchEntities(self.reservation, dict_luis_resultv2)

        if self.reservation.CityDestination is None:
            return await waterfall_step.prompt("DestinationCity",
                                               PromptOptions(
                                                   prompt=MessageFactory.text(
                                                       f" Please enter the date of Departure dd-mm-yyyy")))
        else:

            return await waterfall_step.prompt("DestinationCity",
                                               PromptOptions(
                                                   prompt=MessageFactory.text(
                                                       f" Your Destination CITY  is  {self.reservation.CityDestination}"
                                                       f", is that correct? (yes/no)")))

    async def GetDepartureCity(self, waterfall_step: WaterfallStepContext):

        if self.reservation.CityDeparture is None:
            return await waterfall_step.prompt("DepartureCity",
                                               PromptOptions(
                                                   prompt=MessageFactory.text(
                                                       f" Please enter the Departure CITY")))
        else:
            return await waterfall_step.prompt("DepartureCity",
                                               PromptOptions(
                                                   prompt=MessageFactory.text(
                                                       f" Your city of Departure is  {self.reservation.CityDeparture}"
                                                       f" ,is that correct? (yes/no)")))

    async def GetDepartureDate(self, waterfall_step: WaterfallStepContext):
        if self.reservation.DateDeparture is None:
            return await waterfall_step.prompt("DepartureDate",
                                               PromptOptions(
                                                   prompt=MessageFactory.text(
                                                       f" Please enter the date of Departure dd-mm-yyyy")))
        else:
            return await waterfall_step.prompt("DepartureDate",
                                               PromptOptions(
                                                   prompt=MessageFactory.text(
                                                       f" Your  departure date is  {self.reservation.DateDeparture}"
                                                       f", is that correct? (yes/no)")))

    async def GetReturnDate(self, waterfall_step: WaterfallStepContext):
        if self.reservation.DateReturn is None:
            return await waterfall_step.prompt("ReturnDate",
                                               PromptOptions(
                                                   prompt=MessageFactory.text(
                                                       f" Please enter the date of Return dd-mm-yyyy")))
        else:
            return await waterfall_step.prompt("ReturnDate", PromptOptions(
                prompt=MessageFactory.text(
                    f" Your  return date is  {self.reservation.DateReturn} is that correct? (yes/no)")))

    async def GetBudget(self, waterfall_step: WaterfallStepContext):
        if self.reservation.Budget is None:
            return await waterfall_step.prompt("Budget",
                                               PromptOptions(
                                                   prompt=MessageFactory.text(
                                                       f" Please enter the budget")))
        else:
            return await waterfall_step.prompt("Budget",
                                               PromptOptions(prompt=MessageFactory.text(
                                                   f" Your  Budget is  {self.reservation.Budget}"
                                                   f" ,is that correct? (yes/no)")))

    async def GetParticipant(self, waterfall_step: WaterfallStepContext):
        if self.reservation.NbPeople is None:
            return await waterfall_step.prompt("Participant",
                                               PromptOptions(
                                                   prompt=MessageFactory.text(
                                                       f" Please enter the nb of participant")))
        else:
            return await waterfall_step.prompt("Participant",
                                               PromptOptions(
                                                   prompt=MessageFactory.text(
                                                       f" Your  nb of participant  {self.reservation.NbPeople}"
                                                       f" ,is that correct? (yes/no)")))

    async def Completed(self, waterfall_step: WaterfallStepContext):

        profileinfo = f"here are the information you have entered :\n " \
                      f"CityDeparture = {self.reservation.CityDeparture}" \
                      f"  \n CityDestination {self.reservation.CityDestination}  " \
                      f"\n Budget {self.reservation.Budget}" \
                      f" \n DateReturn {self.reservation.DateReturn} " \
                      f"\n DateDeparture {self.reservation.DateDeparture} " \
                      f" \n NbPeople {self.reservation.NbPeople} \n "
        self.logger.info(f"Complete Reservation CityDeparture = {self.reservation.CityDeparture}" \
                      f"  \n CityDestination {self.reservation.CityDestination}  " \
                      f"\n Budget {self.reservation.Budget}" \
                      f" \n DateReturn {self.reservation.DateReturn} " \
                      f"\n DateDeparture {self.reservation.DateDeparture} " \
                      f" \n NbPeople {self.reservation.NbPeople} \n ")
        await waterfall_step._turn_context.send_activity(profileinfo)
        return await waterfall_step.end_dialog()

    async def on_turn(self, turn_context: TurnContext):
        dialog_context = await self.dialog_set.create_context(turn_context)

        if dialog_context.active_dialog is not None:
            await dialog_context.continue_dialog()
        else:
            await dialog_context.begin_dialog("main_dialog")
        await self.con_statea.save_changes(turn_context)
