package com.pancarte.microservice.service;

import com.pancarte.microservice.model.Borrow;
import com.pancarte.microservice.repository.BookRepository;
import com.pancarte.microservice.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("borrowService")
public class BorrowServiceImpl implements BorrowService {
    @Qualifier("borrowRepository")
    @Autowired
    private BorrowRepository borrowRepository;

    public List<Borrow> findAllBorrowBook() {
        return borrowRepository.findAllBorrowBook();
    }

    public Borrow findBorrowedBook(@Param("id_borrow") int id_borrow) {
        return borrowRepository.findBorrowedBook(id_borrow);
    }



    public List<Borrow> findBorrowedBookByIUser(@Param("id_user") int id_user){
        return borrowRepository.findBorrowedBookByIUser(id_user);
    }
}
