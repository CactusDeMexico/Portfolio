package com.pancarte.climb.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name ="spot")
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idspot")
    private int idspot;

    @Column(name = "idtopo")
    private int idtopo;

    @Column(name = "idpublication")
    private int idpublication;

    @Column(name = "nom")
    private String nomSpot;

    @Column(name = "description")
    private String description;

    @Column(name = "lien")
    private String lienSpot;


}
