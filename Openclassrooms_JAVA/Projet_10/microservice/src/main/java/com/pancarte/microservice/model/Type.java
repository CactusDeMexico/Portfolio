package com.pancarte.microservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name ="type_book")
@Getter
@Setter
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_type")
    private int idType;

    @Column(name = "book_type")
    private String bookType;




}

