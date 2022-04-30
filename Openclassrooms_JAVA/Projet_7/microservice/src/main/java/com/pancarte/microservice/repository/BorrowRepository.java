package com.pancarte.microservice.repository;


import com.pancarte.microservice.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("borrowRepository")
public interface BorrowRepository extends JpaRepository<Borrow, Long> {



    @Query(value = "SELECT * FROM borrow u WHERE u.id_borrow > 0",nativeQuery = true)
    List<Borrow> findAllBorrowBook();

    @Query(value = "SELECT * FROM borrow u WHERE u.id_borrow=:id_borrow",nativeQuery = true)
    Borrow findBorrowedBook(@Param("id_borrow") int id_borrow);

 @Query(value = "SELECT * FROM borrow u WHERE u.id_user=:id_user",nativeQuery = true)
 List<Borrow> findBorrowedBookByIUser(@Param("id_user") int id_user);





}
