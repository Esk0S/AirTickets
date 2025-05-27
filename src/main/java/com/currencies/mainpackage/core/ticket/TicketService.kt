package com.currencies.mainpackage.core.ticket

import com.currencies.mainpackage.api.dto.request.CreateTicketRequest
import com.currencies.mainpackage.api.dto.response.TicketResponse
import java.sql.Date

interface TicketService {

    fun findAll(): List<TicketResponse>

    fun findById(id: Long): TicketResponse?

    fun findTickets(from: String, to: String, startFlightDate: Date): List<TicketResponse>

    fun save(request: CreateTicketRequest): TicketResponse

    fun update(id: Long, request: CreateTicketRequest): TicketResponse

    fun delete(id: Long)

}
