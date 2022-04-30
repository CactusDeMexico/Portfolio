package com.pancarte.architecte.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
/**
 * classe representant un utilisateur
 * @author Marjorie Pancarte
 * @version 1.0
 */
@Entity
@Table(name ="user_account", schema = "public")
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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private int id;



    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "hidden")
    private boolean hidden;




    @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = " id_role"))
    private Set<Role> roles;


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}