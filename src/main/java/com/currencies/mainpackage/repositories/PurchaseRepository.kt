package com.currencies.mainpackage.repositories

import com.currencies.mainpackage.entities.Purchase
import com.currencies.mainpackage.entities.Ticket
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PurchaseRepository : BaseRepository<Purchase, Long> {

    @Query("SELECT p.ticket FROM Purchase p WHERE p.user.id = :userId")
    fun findTicketsByUserId(userId: Long): List<Ticket>

    fun findAllByUserId(userId: Long): List<Purchase>

}