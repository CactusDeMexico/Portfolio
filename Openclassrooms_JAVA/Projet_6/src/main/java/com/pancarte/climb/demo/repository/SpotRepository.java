package com.pancarte.climb.demo.repository;

import com.pancarte.climb.demo.model.Secteur;
import com.pancarte.climb.demo.model.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository("spotRepository")
@Transactional
public interface SpotRepository extends JpaRepository<Spot, Integer> {


    @Query(value = "SELECT * FROM spot u WHERE u.idspot > 0",nativeQuery = true)
    List<Spot> findAllSpot();

    @Query(value = "SELECT idspot FROM spot ORDER BY idspot DESC LIMIT 1;",nativeQuery = true)
    int selectLastIdspot();

    @Query(value = "SELECT * FROM spot u WHERE u.nom Like %:nom%  or description like %:nom%",nativeQuery = true)
    List<Spot> findByName(@Param("nom") String nom);
   // SELECT * FROM spot WHERE nom Like '%gr%' or description like '%ensemble%';
    @Query(value = "SELECT * FROM spot u WHERE u.idtopo =:idtopo",nativeQuery = true)
    List<Spot> findByIdtopo(@Param("idtopo") int idtopo);


}
