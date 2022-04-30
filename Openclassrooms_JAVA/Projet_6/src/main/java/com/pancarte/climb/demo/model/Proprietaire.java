package com.pancarte.climb.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name ="proprietaire")
@Getter
@Setter
public class Proprietaire {

    @Id
    @Column(name = "idtopo")
    private int idtopo;

    @Column(name = "iduser")
    private int iduser;
}
