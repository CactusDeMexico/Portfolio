package com.pancarte.climb.demo.repository;

import com.pancarte.climb.demo.model.Commentaire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("commentaireRepository")
public interface CommentaireRepository extends JpaRepository<Commentaire,Integer> {
    @Query(value = "SELECT * FROM commentaire u WHERE u.idpublication =:idpublication",nativeQuery = true)
    List<Commentaire> findAllByIdPublication(@Param("idpublication") Integer idpublication);
}
