package com.pancarte.architect.front.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * classe representant un utilisateur
 * @author Marjorie Pancarte
 * @version 1.0
 */
@Getter
@Setter
public class User {

    public User(String firstName, String lastName, String email, String password, boolean hidden, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.hidden = hidden;
        this.roles = roles;
    } public User(int id,String firstName, String lastName, String email, String password, boolean hidden, Set<Role> roles) {
        this.id= id ;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.hidden = hidden;
        this.roles = roles;
    }

    public User() {

    }

    private int id;




    private String firstName;

    private String lastName;


    private String email;


    private String password;

    private boolean hidden;


    private Set<Role> roles;


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}