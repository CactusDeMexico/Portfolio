package com.pancarte.architecte.repository;

import com.pancarte.architecte.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repertoire
 */
@Repository("meetingRepository")
public interface MeetingRepository extends JpaRepository<Meeting,Long> {
    @Query(value=" select * from meeting where id_meeting=:id_meeting",nativeQuery = true)
    Meeting queryMeetingById(@Param("id_meeting") int idMeeting);

}
