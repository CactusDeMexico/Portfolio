package com.pancarte.Model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Book_List {

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
        return "Book_ListBean{" +
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
