import datetime
import logging
from opencensus.ext.azure.log_exporter import AzureLogHandler

from config import DefaultConfig


class BookReservation:
    def __init__(self, CityDeparture: str = None,
                 CityDestination: str = None,
                 DateDeparture: str = None,
                 DateReturn: str = None,
                 NbPeople: str = None,
                 Budget: str = None
                 ):
        self.CityDeparture: str = CityDeparture
        self.CityDestination: str = CityDestination
        self.DateDeparture: str = DateDeparture
        self.DateReturn: str = DateReturn
        self.NbPeople: str = NbPeople
        self.Budget: str = Budget
        self.logger = logging.getLogger(__name__)
        CONFIG = DefaultConfig()
        self.logger.addHandler(AzureLogHandler(connection_string=CONFIG.APPINSIGHTS_INSTRUMENTATION))

    def FetchEntities(self, dict):
        print()
        for i in dict["entities"]:
            print(i)
            if "BookReservation_City" in i:
                print("city", dict["entities"][i][0]['City'])
                if "BookReservation_CityDeparture" in i:
                    print("City of departure :", dict["entities"][i][0]['City'])
                    self.CityDeparture = dict["entities"][i][0]['City'][0]
                else:
                    print("City destination :", dict["entities"][i][0]['City'])
                    self.CityDestination = dict["entities"][i][0]['City'][0]
            if "BookReservation_NbPeople" in i:
                print()
            if "BookReservation_Budget" in i:
                print()
                self.Budget = dict["entities"][i][0]['Number'][0] + dict["entities"][i][0]["Currency"][0]
            resa = BookReservation(self.CityDeparture, self.CityDestination, self.DateDeparture, self.DateReturn,
                                   self.NbPeople, self.Budget)
        return resa

    def CheckDate(self, str_value, ref_value):

        now = datetime.datetime.now()
        isValidDate = True
        try:
            dday = datetime.datetime(int(str_value[6:10]), int(str_value[3:5]), int(str_value[0:2]))
        except ValueError:
            isValidDate = False
        if (isValidDate):

            isValidDate = True
        else:
            self.logger.warning(f"Input date is invalid value was {str_value}")

        if len(str_value) == 10:
            if (str_value[2:3] == "-" and str_value[5:6] == "-"):
                if (now > dday):
                    isValidDate = False
                    self.logger.warning(f"Input date is invalid  The departure date should not be already passed :{str_value}")
                if ref_value is not None:

                    ref_day = datetime.datetime(int(ref_value[6:10]), int(ref_value[3:5]), int(ref_value[0:2]))
                    if (ref_day > dday):

                        self.logger.warning(f"Input date is invalid The return date ({str_value}) should not be  after the departure date : {ref_value}")
                        isValidDate = False
            else:
                isValidDate = False
        else:
            isValidDate = False
        return isValidDate
