from datetime import datetime
import sys
import traceback
from http import HTTPStatus

from aiohttp import web
from aiohttp.web import Request, Response, json_response
from botbuilder.core.integration import aiohttp_error_middleware
from botbuilder.core import BotFrameworkAdapter, BotFrameworkAdapterSettings, TurnContext, ConversationState, \
    MemoryStorage, UserState, TelemetryLoggerMiddleware
from botbuilder.schema import Activity, ActivityTypes
from botbuilder.applicationinsights import ApplicationInsightsTelemetryClient
from botbuilder.integration.applicationinsights.aiohttp import (
    AiohttpTelemetryProcessor,
    bot_telemetry_middleware,
)
import asyncio
from adapter_with_error_handler import AdapterWithErrorHandler
from bots import DialogAndWelcomeBot
from config import DefaultConfig
from dialogs import BotDialog
from flight_booking_recognizer import FlightBookingRecognizer
loop = asyncio.get_event_loop()
import nest_asyncio
nest_asyncio.apply()
CONFIG = DefaultConfig()
SETTINGS = BotFrameworkAdapterSettings(CONFIG.APP_ID, CONFIG.APP_PASSWORD)

CONMEMORY = ConversationState(MemoryStorage())
ADAPTER = BotFrameworkAdapter(SETTINGS)
ADAPTER = AdapterWithErrorHandler(SETTINGS, CONMEMORY)
loop = asyncio.get_event_loop()

# Create MemoryStorage, UserState and ConversationState

USER_STATE = UserState(MemoryStorage())


# Create adapter.
# See https://aka.ms/about-bot-adapter to learn more about how bots work.
#ADAPTER.on_turn_error = on_error

# Create telemetry client.
# Note the small 'client_queue_size'.  This is for demonstration purposes.  Larger queue sizes
# result in fewer calls to ApplicationInsights, improving bot performance at the expense of
# less frequent updates.
INSTRUMENTATION_KEY = CONFIG.APPINSIGHTS_INSTRUMENTATION_KEY
TELEMETRY_CLIENT = ApplicationInsightsTelemetryClient(
    INSTRUMENTATION_KEY, telemetry_processor=AiohttpTelemetryProcessor(), client_queue_size=10
)

# Create dialogs and Bot
RECOGNIZER = FlightBookingRecognizer(CONFIG)
CONMEMORY = ConversationState(MemoryStorage())
botdialog = BotDialog(CONMEMORY,RECOGNIZER, telemetry_client=TELEMETRY_CLIENT)
#TELEMETRY_logging_MIDDLEWARE = TelemetryLoggerMiddleware(telemetry_client=TELEMETRY_CLIENT, log_personal_information=True)
#ADAPTER.use(TELEMETRY_logging_MIDDLEWARE)

BOT = DialogAndWelcomeBot(
    CONMEMORY, USER_STATE, botdialog, TELEMETRY_CLIENT)

async def messages(request: Request) -> Response:
    if "application/json" in request.headers["content-type"]:
        body = await request.json()
    else:
        return Response(status = 415)

    async def call_fun(turncontext):
        await botdialog.on_turn(turncontext)

    activity = Activity().deserialize(body)
    auth_header = request.headers["Authorization"] if "Authorization" in request.headers else ""
    response = await ADAPTER.process_activity(activity, auth_header,  call_fun)
    if response:
        return json_response(data=response.body, status=response.status)
    return Response(status=HTTPStatus.OK)



"""
    async def call_fun(turncontext):
        await botdialog.on_turn(turncontext)
    try:
        task = loop.create_task(
            ADAPTER.process_activity(activity, auth_header, call_fun)
        )
        loop.run_until_complete(task)
        # return json_response(data=response.body, status=response.status)
        return Response(status=200)
    except Exception as exception:
        raise exception"""



def init_func(argv):
    app = web.Application(middlewares=[bot_telemetry_middleware, aiohttp_error_middleware])
    app.router.add_post("/api/messages", messages)
    return app


if __name__ == "__main__":
    app = init_func(None)

    try:
        web.run_app(app,  host="0.0.0.0", port=CONFIG.PORT)

    except Exception as error:
        raise error
#python3.9 -m aiohttp.web -H 0.0.0.0 -P 8000 app:init_func
# gunicorn --bind 0.0.0.0 --worker-class aiohttp.worker.GunicornWebWorker --timeout 600 app:APP