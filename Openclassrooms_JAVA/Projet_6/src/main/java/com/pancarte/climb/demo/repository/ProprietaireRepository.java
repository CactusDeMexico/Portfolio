package com.pancarte.climb.demo.repository;

import com.pancarte.climb.demo.model.Proprietaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("proprietaireRepository")
public interface ProprietaireRepository extends JpaRepository<Proprietaire,Integer> {
    @Query(value = "SELECT * FROM proprietaire ",nativeQuery = true)
    List<Proprietaire> findAllPro();


}
