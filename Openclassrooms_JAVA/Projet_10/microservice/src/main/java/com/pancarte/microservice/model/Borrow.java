package com.pancarte.microservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "borrow")
@Getter
@Setter
public class Borrow {

    public Borrow(int idBook, int idUser, boolean isLoan, Date creationDate, Date returnDate, boolean isExtended) {
        this.idBook = idBook;
        this.idUser = idUser;
        this.isLoan = isLoan;
        this.creationDate = creationDate;
        this.returnDate = returnDate;
        this.isExtended = isExtended;
    }
    public Borrow(int idBorrow ,int idBook, int idUser, boolean isLoan, Date creationDate, Date returnDate, boolean isExtended) {
        this.idBorrow = idBorrow;
        this.idBook = idBook;
        this.idUser = idUser;
        this.isLoan = isLoan;
        this.creationDate = creationDate;
        this.returnDate = returnDate;
        this.isExtended = isExtended;
    }

    public Borrow() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_borrow")
    private int idBorrow;

    @Column(name = "id_book")
    private int idBook;

    @Column(name = "id_user")
    private int idUser;

    @Column(name = "is_loan")
    private boolean isLoan;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "borrowing_date")
    private Date creationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "is_extended")
    private  boolean isExtended;

}
