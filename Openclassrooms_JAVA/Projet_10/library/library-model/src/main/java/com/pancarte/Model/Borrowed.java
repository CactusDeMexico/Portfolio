package com.pancarte.Model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
public class Borrowed {

    private int idBorrowed;


    private int idBook;


    private int idUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd")

    private Date creationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;



}
