package com.pancarte.architecte.repository;

import com.pancarte.architecte.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repertoire
 */
@Repository("materialRepository")
public interface MaterialRepository  extends JpaRepository<Material, Long> {
    Material findByName(String material);
    @Query(value=" select * from material where id_material=:id_material",nativeQuery = true)
    Material queryMaterialById(@Param("id_material") int idMaterial);



}
