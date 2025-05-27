package com.currencies.mainpackage.repositories

import com.currencies.mainpackage.entities.Ticket
import java.sql.Date
import org.springframework.data.jpa.repository.Query

interface TicketRepository : BaseRepository<Ticket, Long> {

    @Query("from Ticket where fromPlace = :from AND toPlace = :to AND DATE(startFlightDate) = :startFlightDate")
    fun findTickets(from: String, to: String, startFlightDate: Date): List<Ticket>

}
