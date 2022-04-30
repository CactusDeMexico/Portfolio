package com.pancarte.architecte.repository;

import com.pancarte.architecte.model.BlockedMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repertoire
 */
@Repository("blockedMailRepository")
public interface BlockedMailRepository extends JpaRepository<BlockedMail, Long> {


}
