package com.pancarte.climb.demo.repository;


import com.pancarte.climb.demo.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



@Repository("rentRepository")
public interface RentRepository extends JpaRepository<Rent,Integer> {




}
