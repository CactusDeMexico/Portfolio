package com.pancarte.microservice.repository;


import com.pancarte.microservice.model.Borrowed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("borrowedRepository")
public interface BorrowedRepository extends JpaRepository<Borrowed, Long> {


    @Query(value = "SELECT * FROM borrowed u WHERE u.id_borrow > 0", nativeQuery = true)
    List<Borrowed> findAllBorrowBook();

    @Query(value = "SELECT MIN(u.id_borrowed) FROM borrowed u WHERE u.id_book=:id_book", nativeQuery = true)
   Integer findFirstBorrowById(@Param("id_book") int id_book);

    @Query(value = "SELECT * FROM borrowed u WHERE u.id_borrowed=:id_borrowed", nativeQuery = true)
    Borrowed findBorrowedBook(@Param("id_borrowed") int id_borrowed);
}
