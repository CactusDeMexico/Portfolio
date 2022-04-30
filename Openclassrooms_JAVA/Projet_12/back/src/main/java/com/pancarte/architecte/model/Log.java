package com.pancarte.architecte.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    private static final Logger logger = LogManager.getLogger(Log.class);

    public void setInfo(String email,String purpose, String text) {
        System.out.println("je suis la");
        logger.info(" Mail de :" + email);
        logger.info(" le sujet:" +purpose);

        logger.info(text);

    }


   public void resultMailFail(String email,String purpose) {
        logger.error(" Tentative de prise de rendez-vous échouée");
        logger.error("à l'addresse email :"+email);
        logger.error("le sujet etait :"+purpose);
    } public void cancelMail(String email,String purpose) {
        logger.error(" Tentative de prise de rendez-vous échouée");
        logger.error("à l'addresse email :"+email);
        logger.error("le sujet etait :"+purpose);
    }
    public void test(String t){
        logger.warn(t);
    }
}