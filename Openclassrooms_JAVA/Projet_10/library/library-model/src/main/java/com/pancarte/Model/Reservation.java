package com.pancarte.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Reservation {

    private int idreservation;

    private int idBook;

    private int idUser;

    private String title;

    private boolean emailSended;

    private Date lastDate;


}
