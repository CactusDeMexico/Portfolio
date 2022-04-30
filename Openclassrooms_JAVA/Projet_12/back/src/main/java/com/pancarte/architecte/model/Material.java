package com.pancarte.architecte.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
/**
 * classe representant  un materiaux
 * @author Marjorie Pancarte
 * @version 1.0
 */
@Entity
@Table(name ="material", schema = "public")
@Getter
@Setter
public class Material {
    public Material() {
    }

    public Material(int id, String name, int thickness, boolean opaque) {
        this.id = id;
        this.name = name;
        this.thickness = thickness;
        this.opaque = opaque;
    }
    public Material( String name, int thickness, boolean opaque) {

        this.name = name;
        this.thickness = thickness;
        this.opaque = opaque;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_material")
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "thickness")
    private int thickness;

    @Column(name = "opaque")
    private boolean opaque;


}
