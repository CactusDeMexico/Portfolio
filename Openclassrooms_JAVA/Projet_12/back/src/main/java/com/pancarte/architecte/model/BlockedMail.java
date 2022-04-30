package com.pancarte.architecte.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * BlockedMail classe representant les emails bloqué et leurs causes
 * @author Marjorie Pancarte
 * @version 1.0
 */
@Entity
@Table(name = "blocked_mail", schema = "public")
@Getter
@Setter
public class BlockedMail {
    public BlockedMail() {
    }

    public BlockedMail(int id, String email, String cause) {
        this.id= id ;
        this.email = email;
        this.cause = cause;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_blocked_mail")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "cause")
    private String cause;
}
