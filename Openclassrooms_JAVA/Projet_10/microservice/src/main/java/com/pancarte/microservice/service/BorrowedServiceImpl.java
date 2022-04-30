package com.pancarte.microservice.service;

import com.pancarte.microservice.model.Borrowed;
import com.pancarte.microservice.repository.BorrowedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("borrowedService")
public class BorrowedServiceImpl implements BorrowedService {

    @Autowired
    @Qualifier("borrowedRepository")
    public BorrowedRepository borrowedRepository;

    @Override
    public List<Borrowed> findAllBorrowBook() {
        return borrowedRepository.findAllBorrowBook();
    }

    @Override
    public Integer findFirstBorrowById(@Param("id_book") int id_book) {
        return borrowedRepository.findFirstBorrowById(id_book);
    }

    @Override
    public Borrowed findBorrowedBook(@Param("id_borrowed") int id_borrowed) {
        return borrowedRepository.findBorrowedBook(id_borrowed);
    }
}
