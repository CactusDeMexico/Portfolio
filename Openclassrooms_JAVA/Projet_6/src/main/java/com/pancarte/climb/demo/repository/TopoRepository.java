package com.pancarte.climb.demo.repository;

import com.pancarte.climb.demo.model.Proprietaire;
import com.pancarte.climb.demo.model.Topo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("topoRepository")
@Transactional
public interface TopoRepository extends JpaRepository<Topo, Integer> {


    @Query(value = "SELECT * FROM topo u WHERE u.idtopo > 0",nativeQuery = true)
    List<Topo> findAllTopo();
    @Query(value = "SELECT idtopo FROM topo ORDER BY idtopo DESC LIMIT 1;",nativeQuery = true)
    int selectLastIdTopo();

    @Query(value = "SELECT * FROM topo u WHERE u.lieu Like %:nom%",nativeQuery = true)
    List<Topo> findByLieu(@Param("nom") String nom);

    @Query(value = "SELECT * FROM topo u WHERE u.idtopo =:idtopo",nativeQuery = true)
    List<Topo> findById(@Param("idtopo") int idtopo);
    @Query(value = "SELECT * FROM topo u WHERE u.idtopo =:idtopo",nativeQuery = true)
    Topo findOneById(@Param("idtopo") int idtopo);

    @Query(value = "SELECT * FROM proprietaire u WHERE u.idtopo =:idtopo",nativeQuery = true)
    List<Proprietaire> findOwner(@Param("idtopo") int idtopo);
    @Query(value = "SELECT * FROM proprietaire ",nativeQuery = true)
    List<Proprietaire> findOwners();



//UPDATE  rent u SET description =:description where description=:oldDescription
}
