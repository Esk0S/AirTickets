package com.currencies.mainpackage.repositories

import com.currencies.mainpackage.entities.Ticket
import com.currencies.mainpackage.entities.jpa.JpaCity
import java.sql.Date
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TicketRepository : BaseRepository<Ticket, Long> {
    @Query("from Ticket where fromPlace = :from AND toPlace = :to AND DATE(startFlightDate) = :startFlightDate")
    fun findTickets(from: JpaCity, to: JpaCity, startFlightDate: Date): List<Ticket>
}
