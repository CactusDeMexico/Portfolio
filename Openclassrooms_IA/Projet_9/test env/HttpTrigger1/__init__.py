import logging

import azure.functions as func
import os 
import pandas as pd
from random import random
def main(req: func.HttpRequest) -> func.HttpResponse:
    logging.info('Python HTTP trigger function processed a request.')
    dir_path = os.path.dirname(os.path.realpath(__file__))
    logging.info('%s cuurent path',dir_path)
    arr = os.listdir(dir_path)
    logging.info('%s liste fichier',arr)
 

    userId = req.params.get('userId')


    logging.info(f'utilisateur :{userId} ')
    df_recommendation = pd.read_csv(dir_path+"/df_recommendations.csv", sep=",")
    exist= len(df_recommendation[df_recommendation['user_id']==userId])
    logging.info(f'{exist}')
    if int(userId) in df_recommendation['user_id'].values :
        list_art = []
        article_list_user = df_recommendation[df_recommendation['user_id']==int(userId)]
        article_lists =article_list_user['article_list'].iloc[0]
        return func.HttpResponse(f"{article_lists}")
    else:
        logging.info(f'utilisateur sans article: ')

        default_list = [281,375,340,43,99]
        return func.HttpResponse(f"{default_list}")

