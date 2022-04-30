package com.pancarte.microservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name ="book")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_book")
    private int idBook;

    @Column(name = "id_type")
    private int idType;

    @Column(name = "id_editeur")
    private int idEditeur;

    @Column(name = "title")
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "url_image")
    private String urlImage;

    @Column(name = "isbn")
    private String isbn;

    @DateTimeFormat(pattern = "yyyy")
    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "price")
    private int price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "creation_date")
    private Date creationDate;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_date")
    private Date updateDate;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "id_editeur"), inverseJoinColumns = @JoinColumn(name = "id_book"))
    private Set<Author> authors;


    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

}
