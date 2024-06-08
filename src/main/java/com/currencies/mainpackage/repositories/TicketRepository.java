package com.currencies.mainpackage.repositories;

import com.currencies.mainpackage.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query("from Ticket where fromPlace = :from AND toPlace = :to AND DATE(startFlightDate) = :startFlightDate")
    List<Ticket> findTickets(String from, String to, Date startFlightDate);
}
