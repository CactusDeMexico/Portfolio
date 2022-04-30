package com.pancarte.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class Book {

    private int idBook;


    private int idType;


    private int idEditeur;


    private String title;


    private String summary;


    private String urlImage;


    private String isbn;


    private Date purchaseDate;


    private int price;


    private Date creationDate;



    private Date updateDate;



    private Set<Author> authors;


    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

}
