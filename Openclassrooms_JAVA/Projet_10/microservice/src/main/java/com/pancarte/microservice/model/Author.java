package com.pancarte.microservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name ="author")
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_editeur")
    private int idAuthor;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "first_name")
    private String first_name;


}

