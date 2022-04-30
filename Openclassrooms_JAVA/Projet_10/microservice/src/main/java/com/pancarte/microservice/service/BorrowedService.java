package com.pancarte.microservice.service;

import com.pancarte.microservice.model.Borrowed;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowedService {

    public List<Borrowed> findAllBorrowBook();

    public Integer findFirstBorrowById(@Param("id_book") int id_book);

    public Borrowed findBorrowedBook(@Param("id_borrowed") int id_borrowed);
}
