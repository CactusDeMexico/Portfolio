package com.pancarte.architecte.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
/**
 * classe representant un devis
 * @author Marjorie Pancarte
 * @version 1.0
 */
@Entity
@Table(name ="quote", schema = "public")
@Getter
@Setter
public class Quote {
    public Quote() {
    }

    public Quote(int id, int refProject, String email) {

        this.id= id ;
        RefProject = refProject;
        this.email = email;

    }

    public Quote(int refProject, String email) {
        RefProject = refProject;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_quote")
    private int id;

    @Column(name = "id_ref_project")
    private int RefProject;


    @Column(name = "email")
    private String email;



}
