package com.pancarte.architecte.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
/**
 * classe representant un projet
 * @author Marjorie Pancarte
 * @version 1.0
 */
@Entity
@Table(name ="project", schema = "public")
@Getter
@Setter
public class Project {
    public Project() {
    }

    public Project(int id, String projectName, String description, String type, int surface, String urlImg, boolean hidden, Set<Material> materials) {

        this.id= id ;
        this.projectName = projectName;
        this.description = description;
        this.type = type;
        this.surface = surface;
        this.urlImg = urlImg;
        this.hidden = hidden;
        this.materials = materials;
    }

    public Project(String projectName, String description, String type, int surface, String urlImg, boolean hidden, Set<Material> materials) {
        this.projectName = projectName;
        this.description = description;
        this.type = type;
        this.surface = surface;
        this.urlImg = urlImg;
        this.hidden = hidden;
        this.materials = materials;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_project")
    private int id;

    @Column(name = "name")
    private String projectName;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "surface")
    private int surface;
    @Column(name = "urlImg")
    private String urlImg;

    @Column(name = "hidden")
    private boolean hidden;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_material", joinColumns = @JoinColumn(name = "id_project"), inverseJoinColumns = @JoinColumn(name = " id_material"))
    private Set<Material> materials;


    public Set<Material> getMaterial() {
        return materials;
    }

    public void setMaterials(Set<Material> materials) {
        this.materials = materials;
    }
}
