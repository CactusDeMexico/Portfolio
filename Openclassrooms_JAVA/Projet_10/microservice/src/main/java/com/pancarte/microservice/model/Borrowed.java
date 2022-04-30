package com.pancarte.microservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "borrowed")
@Getter
@Setter
public class Borrowed {
    public Borrowed(int idBook, int idUser, Date creationDate, Date returnDate) {
        this.idBook = idBook;
        this.idUser = idUser;
        this.creationDate = creationDate;
        this.returnDate = returnDate;
    }
    public Borrowed(int idBorrowed,int idBook, int idUser, Date creationDate, Date returnDate) {
        this.idBorrowed =idBorrowed;
        this.idBook = idBook;
        this.idUser = idUser;
        this.creationDate = creationDate;
        this.returnDate = returnDate;
    }
    public Borrowed() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_borrowed")
    private int idBorrowed;

    @Column(name = "id_book")
    private int idBook;

    @Column(name = "id_user")
    private int idUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "borrowing_date")
    private Date creationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "return_date")
    private Date returnDate;



}
