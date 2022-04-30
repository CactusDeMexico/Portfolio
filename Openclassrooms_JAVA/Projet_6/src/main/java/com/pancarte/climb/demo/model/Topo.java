package com.pancarte.climb.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "topo")
@Getter
@Setter
public class Topo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idtopo")
    private int idtopo;

    @Column(name = "lieu")
    private String lieuTopo;

    @Column(name = "hiddentopo")
    private boolean isHidden;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "proprietaire", joinColumns = @JoinColumn(name = "idtopo"), inverseJoinColumns = @JoinColumn(name = "iduser"))
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
