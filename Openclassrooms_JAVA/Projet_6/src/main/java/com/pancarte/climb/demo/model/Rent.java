package com.pancarte.climb.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name = "rent")
@Getter
@Setter
public class Rent {
    @Id
    @Column(name = "idtopo")
    private int idtopo;

    @Column(name = "isloan")
    private boolean isloan;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "borrowingdate")
    private Date creationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "return")
    private Date returnDate;

    @Column(name = "iduser")
    private int iduser;

    @Column(name = "isborrow")
    private boolean isBorrow;

    @Column(name = "isseen")
    private boolean isSeen;
}
