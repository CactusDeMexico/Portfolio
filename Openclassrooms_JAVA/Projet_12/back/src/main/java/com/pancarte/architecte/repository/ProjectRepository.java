package com.pancarte.architecte.repository;

import com.pancarte.architecte.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repertoire
 */
@Repository("projectRepository")
public interface ProjectRepository extends JpaRepository <Project,Long> {
    @Query(value=" select * from project where id_project=:id_project",nativeQuery = true)
    Project queryProjectById(@Param("id_project") int idProject);
}
