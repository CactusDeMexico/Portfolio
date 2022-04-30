package com.pancarte.microservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Book_Reservation {


    private int idUser;

    private String title;

    private int ranking;

    private int nbres;

    private int nbMaxRes;

    private Date avalaibleDate;

}
