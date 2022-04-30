package com.pancarte.architecte.model;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.Properties;
@Getter
@Setter
public
class ConfigReader {
    private boolean personlaMail;
    private String texte;

    public ConfigReader() throws Exception {
        Properties properties = new Properties();

        InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
        properties.load(input);

        try {

            personlaMail = Boolean.valueOf(properties.getProperty("personlaMail", "false"));
            if (personlaMail) {
                texte= properties.getProperty("texte", "Message personnalisé");
            } else {

            }
        } catch (NumberFormatException e) {
            System.out.println("Error while reading configuration.");
        }
    }


}
