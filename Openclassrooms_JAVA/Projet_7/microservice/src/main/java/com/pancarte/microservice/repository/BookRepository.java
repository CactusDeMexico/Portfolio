package com.pancarte.microservice.repository;


import com.pancarte.microservice.model.Book;
import com.pancarte.microservice.model.Borrow;
import com.pancarte.microservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Long> {


    @Query(value = "SELECT * FROM book u WHERE u.id_book > 0",nativeQuery = true)
    List<Book> findAll();

    @Query(value = "SELECT * FROM borrow u WHERE u.id_borrow > 0",nativeQuery = true)
    List<Borrow> findAllBorrowBook();

    @Query(value = "SELECT * FROM book u WHERE u.title Like %:name%",nativeQuery = true)
    List<Book> findByTitle(@Param("name") String name);

    @Query(value = "SELECT * FROM book u WHERE u.id_book=:id_book",nativeQuery = true)
    Book findById(@Param("id_book") int id_book);


}
