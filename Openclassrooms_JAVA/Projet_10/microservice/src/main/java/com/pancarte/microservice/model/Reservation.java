package com.pancarte.microservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_reservation")
    private int idreservation;

    @Column(name = "id_book")
    private int idBook;

    @Column(name = "id_user")
    private int idUser;

    @Column(name = "title")
    private String title;

    @Column(name = "emailsended")
    private boolean emailSended;

    @Column(name = "lastdate")
    private Date lastDate;
}
