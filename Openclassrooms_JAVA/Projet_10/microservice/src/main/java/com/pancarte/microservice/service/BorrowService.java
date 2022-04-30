package com.pancarte.microservice.service;

import com.pancarte.microservice.model.Borrow;
import com.pancarte.microservice.model.Borrowed;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BorrowService {



   public List<Borrow> findAllBorrowBook();

    public  Borrow findBorrowedBook(@Param("id_borrow") int id_borrow);

    public List<Borrow> findBorrowedBookByIUser(@Param("id_user") int id_user);
}
