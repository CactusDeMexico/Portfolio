package com.pancarte.Model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;


@Getter
@Setter
public class User {

    private int id;


    private String name;


    private String lastName;

    private String email;


    private String password;

    private int active;


    private Date creationDate;


    private Date updateDate;


    private Set<Role> roles;


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}