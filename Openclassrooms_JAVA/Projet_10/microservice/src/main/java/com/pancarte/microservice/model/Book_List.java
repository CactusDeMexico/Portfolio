package com.pancarte.microservice.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Book_List {
    public Book_List(int idBook, int idType, int idEditeur, String title, String summary, String urlImage, String isbn, Date purchaseDate, int price, Date creationDate, Date updateDate, int nbCopy, int nbCopyAvailable) {
        this.idBook = idBook;
        this.idType = idType;
        this.idEditeur = idEditeur;
        this.title = title;
        this.summary = summary;
        this.urlImage = urlImage;
        this.isbn = isbn;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.nbCopy = nbCopy;
        this.nbCopyAvailable = nbCopyAvailable;
    }

    public Book_List() {
    }

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

    private int nbCopy;

    private int nbCopyAvailable;

    @Override
    public String toString() {
        return "Book_List{" +
                "idBook=" + idBook +
                ", idType=" + idType +
                ", idEditeur=" + idEditeur +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", isbn='" + isbn + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", price=" + price +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", nbCopy=" + nbCopy +
                ", nbCopyAvailable=" + nbCopyAvailable +
                '}';
    }

}
