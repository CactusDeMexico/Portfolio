package com.pancarte.microservice.service;


import com.pancarte.microservice.model.Book;
import com.pancarte.microservice.model.Borrow;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("bookService")
public interface BookService {


   public List<Book> findAll();
   public List<Book> borrowBook();
   Book findById(@Param("id_book") int id_book);
   public List<Borrow> findAllBorrowBook();
   List<Book> findByTitle(@Param("name") String name);



}
