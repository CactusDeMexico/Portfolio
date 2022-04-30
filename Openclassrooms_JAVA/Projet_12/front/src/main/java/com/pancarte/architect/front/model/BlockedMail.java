package com.pancarte.architect.front.model;

import lombok.Getter;
import lombok.Setter;
/**
 * BlockedMail classe representant les emails bloqué et leurs causes
 * @author Marjorie Pancarte
 * @version 1.0
 */
@Getter
@Setter
public class BlockedMail {

    private int id;


    private String email;


    private String cause;
}
