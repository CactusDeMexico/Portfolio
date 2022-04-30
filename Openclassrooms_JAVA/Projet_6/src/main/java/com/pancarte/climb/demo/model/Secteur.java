package com.pancarte.climb.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name ="secteur")
public class Secteur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idsecteur")
    private int idsecteur;

    @Column(name = "idspot")
    private int idspot;


    @Column(name = "idpublication")
    private int idpublication;


    @Column(name = "nom")
    private String nomSecteur;

    @Column(name = "lieu")
    private String lieuSecteur;

    @Column(name = "type")
    private String type;

    @Column(name = "lien")
    private String lien;

    @Column(name = "hauteur")
    private int hauteur;
}
