package com.pancarte.climb.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name ="voie")
public class Way {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idvoie")
    private int idvoie;

    @Column(name = "idsecteur")
    private int idsecteur;

    @Column(name = "nom")
    private String nomWay;

    @Column(name = "equipees")
    private boolean equipees;

    @Column(name = "relai")
    private String relai;

    @Column(name = "cotation")
    private String cotation;
}
