from botbuilder.core import TurnContext,ActivityHandler,RecognizerResult,MessageFactory
from botbuilder.ai.luis import LuisApplication,LuisPredictionOptions,LuisRecognizer


class LuisBot(ActivityHandler):
    def __init__(self):
        luis_app = LuisApplication("21fd35f2-6456-4c92-80fe-e14f1e4ef693","f071a3abb14948778dea6db7d4f63034","https://westeurope.api.cognitive.microsoft.com/")
        luis_option = LuisPredictionOptions(include_all_intents=True,include_instance_data=True)
        self.LuisReg = LuisRecognizer(luis_app,luis_option,True)

 

    async def on_message_activity(self,turn_context:TurnContext):
        luis_result = await self.LuisReg.recognize(turn_context)
        intent = LuisRecognizer.top_intent(luis_result)
        print('intent', type(intent))
        await turn_context.send_activity(f"Top Intent : {intent}")

        result = luis_result.properties["luisResult"]
        print('result',type(result))

        await turn_context.send_activity(f" Luis Result {result.entities[0]}")
