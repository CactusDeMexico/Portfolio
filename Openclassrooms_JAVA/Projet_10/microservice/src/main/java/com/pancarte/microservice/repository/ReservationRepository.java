package com.pancarte.microservice.repository;

import com.pancarte.microservice.model.Book;
import com.pancarte.microservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reservationRepository")
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT * FROM reservation u WHERE u.title Like %:name%",nativeQuery = true)
    List<Reservation> findAllReservationByBook(@Param("name") String name);

    @Query(value = "SELECT * FROM reservation u WHERE u.id_reservation=:id_reservation",nativeQuery = true)
    Reservation findReservationById(@Param("id_reservation") int idReservation);
    @Query(value = "SELECT * FROM reservation u WHERE u.id_user=:id_user AND u.title=:title",nativeQuery = true)
    Reservation findReservation(@Param("id_user") int id_user,@Param("title") String title);

    @Query(value = "SELECT * FROM reservation u WHERE u.id_reservation>0",nativeQuery = true)
    List<Reservation> findAllReservation();

}
