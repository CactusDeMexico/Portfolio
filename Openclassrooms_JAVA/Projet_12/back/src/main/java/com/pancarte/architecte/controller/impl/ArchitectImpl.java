package com.pancarte.architecte.controller.impl;

import com.dummy.myerp.technical.exception.FunctionalException;
import com.pancarte.architecte.controller.ArchitectController;
import com.pancarte.architecte.model.Log;
import com.pancarte.architecte.model.User;
import com.pancarte.architecte.repository.*;
import com.pancarte.architecte.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.ConstraintViolationException;
import java.util.List;

public class ArchitectImpl {

    public ArchitectImpl() {
    }

    /**
     * Cree un utilisateur
     *
     * @param lastName  le nom de famillle de l'utilisateur
     * @param firstName le prenom  de l'utilisateur
     * @param email     l'email de l'utilisateur
     * @param password  le mot de passe de l'utilisateur
     */
    public void createUser(String lastName,
                           String firstName,
                           String email,
                           String password) throws FunctionalException {
        if (lastName.isEmpty() || firstName.isEmpty()
                || email.isEmpty() || password.isEmpty()) {
            throw new FunctionalException("Les champs ne peuvent etre vide.");
        }
    }

    public void queryUserById(int idUser) throws FunctionalException {
        if (idUser == 0) {
            throw new FunctionalException("L'id ne peut etre 0");
        }
        if (idUser < 0) {
            throw new FunctionalException("L'id ne peut pas etre inferieur 0");
        }
    }
    public void queryUserByEmail(String email)throws FunctionalException {
            if ( email.isEmpty()) {
                throw new FunctionalException("Les champs ne peuvent etre vide.");
            }
    }

    void sendEmail(String email, String subject, String text) throws FunctionalException {
        if (!email.contains("@") || !email.contains(".") || !email.contains("@")) {
            throw new FunctionalException("email invalide");
        }
        if (subject.length() < 5) {
            throw new FunctionalException("veuillez rentrer un sujet detaillé");
        }
        if (text.length() < 5) {
            throw new FunctionalException("veuillez detaillé votre texte");
        }
        if ( email.isEmpty() || text.isEmpty() || subject.isEmpty()) {
            throw new FunctionalException("Les champs ne peuvent etre vide");
        }
    }
}
