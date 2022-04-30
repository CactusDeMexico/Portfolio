package com.pancarte.Model;

import lombok.Getter;
import lombok.Setter;


import java.util.Date;


@Getter
@Setter
public class Borrow {

    private int idBorrow;


    private int idBook;


    private int idUser;


    private boolean isLoan;


    private Date creationDate;

    private Date returnDate;


    private  boolean isExtended;

}
