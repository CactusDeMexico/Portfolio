package com.pancarte.architect.front.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
/**
 * classe representant un projet
 * @author Marjorie Pancarte
 * @version 1.0
 */
@Getter
@Setter
public class Project {

    private int id;

    private String projectName;



    private String description;


    private String type;


    private int surface;
    private String urlImg;


    private boolean hidden;
    private Set<Material> materials;


    public Set<Material> getMaterial() {
        return materials;
    }

    public void setMaterials(Set<Material> materials) {
        this.materials = materials;
    }
}
