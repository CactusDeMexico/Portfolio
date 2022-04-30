package com.pancarte.climb.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name ="commentaire")
@Getter
@Setter
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idcommentaire")
    private int idcommentaire;

    @Column(name = "iduser")
    private int iduser;

    @Column(name = "texte")
    private String texte;


    @Column(name = "idpublication")
    private int idpublication;

    @Column(name = "hiddencom")
    private boolean isHidden;
}
