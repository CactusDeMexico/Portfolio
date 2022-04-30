package com.pancarte.microservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name ="editor")
@Getter
@Setter
public class Editor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_editeur")
    private int idEditeur;

    @Column(name = "name")
    private String name;




}

