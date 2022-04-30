package com.pancarte.microservice.service;

import com.pancarte.microservice.model.Book;

import com.pancarte.microservice.model.Borrow;
import com.pancarte.microservice.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

    @Qualifier("bookRepository")
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> borrowBook() {

        List<Book> books = bookRepository.findAll();
        List<Borrow> borrowedBook = bookRepository.findAllBorrowBook();
        List<Book> availableBooks = books;
        return availableBooks;
    }

    @Override
    public Book findById(int id_book) { return bookRepository.findById(id_book);}

    @Override
    public List<Borrow> findAllBorrowBook() {
        return bookRepository.findAllBorrowBook();
    }

    @Override
    public List<Book> findByTitle(String name) {
        return bookRepository.findByTitle(name);
    }
}