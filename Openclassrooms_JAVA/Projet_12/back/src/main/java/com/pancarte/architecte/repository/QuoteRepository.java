package com.pancarte.architecte.repository;

import com.pancarte.architecte.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


    @Repository("quoteRepository")
    public interface QuoteRepository extends JpaRepository<Quote,Long> {

    }

