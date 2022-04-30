package com.pancarte.architecte.repository;

import com.pancarte.architecte.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repertoire
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRole(String role);

    @Query(value="select u.email, r.role from user_account u inner join user_role ur on (u.id_user = ur.id_user) inner join role r on (ur.id_role=r.id_role) where u.email=:email" ,nativeQuery = true)
    String queryRole(@Param("email") String email);
}
