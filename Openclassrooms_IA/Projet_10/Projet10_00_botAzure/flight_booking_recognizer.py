# Copyright (c) Microsoft Corporation. All rights reserved.
# Licensed under the MIT License.

from botbuilder.ai.luis import LuisApplication, LuisRecognizer, LuisPredictionOptions, LuisRecognizerOptionsV3
from botbuilder.core import (
    Recognizer,
    RecognizerResult,
    TurnContext,
    BotTelemetryClient,
    NullTelemetryClient,
)

from config import DefaultConfig


class FlightBookingRecognizer(Recognizer):
    def __init__(
        self, configuration: DefaultConfig, telemetry_client: BotTelemetryClient = None
    ):
        self._recognizer = None
        self._recognizerv2 = None
        luis_is_configured = (
            configuration.LUIS_APP_ID
            and configuration.LUIS_API_KEY
            and configuration.LUIS_API_HOST_NAME
        )
        if luis_is_configured:
            # Set the recognizer options depending on which endpoint version you want to use e.g v2 or v3.
            # More details can be found in https://docs.microsoft.com/azure/cognitive-services/luis/luis-migration-api-v3
            luis_application = LuisApplication(
                configuration.LUIS_APP_ID,
                configuration.LUIS_API_KEY,
                configuration.LUIS_API_HOST_NAME,
            )
            optionv2 = LuisRecognizerOptionsV3(include_all_intents=True)
            options=LuisPredictionOptions()

            options.telemetry_client = telemetry_client or NullTelemetryClient()

            self._recognizer = LuisRecognizer(
                luis_application, prediction_options=options
            )
            optionv2.telemetry_client = telemetry_client or NullTelemetryClient()

            self._recognizerv2=LuisRecognizer(
                luis_application, prediction_options= optionv2
            )
    @property
    def is_configured(self) -> bool:
        # Returns true if luis is configured in the config.py and initialized.
        return self._recognizer is not None

    async def recognize(self, turn_context: TurnContext) -> RecognizerResult:
        return await self._recognizer.recognize(turn_context)

    async def recognizev2(self, turn_context: TurnContext) -> RecognizerResult:
        return await self._recognizerv2.recognize(turn_context)
