package com.pancarte.climb.demo.repository;

import com.pancarte.climb.demo.model.Publication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("publicationRepository")
public interface PublicationRepository extends JpaRepository<Publication, Integer> {



    @Query(value = "SELECT idpublication FROM publication ORDER BY idpublication DESC LIMIT 1;",nativeQuery = true)
    int selectLastIdPublication();

    @Query(value = "SELECT * FROM publication u WHERE u.idpublication =:idpublication",nativeQuery = true)
    Publication findAllById(@Param("idpublication") Integer idpublication);

    @Query(value = "SELECT * FROM publication u WHERE u.iduser =:iduser",nativeQuery = true)
    List<Publication> findByIdUser(@Param("iduser") Integer iduser);

    @Query(value = "SELECT * FROM publication u WHERE u.name =:name",nativeQuery = true)
    List<Publication> findByName(@Param("name") String name);
}
