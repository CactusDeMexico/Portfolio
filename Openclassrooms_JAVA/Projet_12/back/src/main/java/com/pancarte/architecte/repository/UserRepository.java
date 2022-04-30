package com.pancarte.architecte.repository;


import com.pancarte.architecte.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repertoire
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findById(int id);

    @Query(value = "SELECT * FROM user_account u WHERE u.id_user > 0",nativeQuery = true)
    List<User> findAll();

    @Query(value=" select * from user_account where email=:email",nativeQuery = true)
    User queryUser(@Param("email") String email);

}
