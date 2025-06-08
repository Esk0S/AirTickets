package com.currencies.mainpackage.core.ticket.implementation

import com.currencies.mainpackage.api.dto.request.CreateTicketRequest
import com.currencies.mainpackage.api.dto.response.TicketResponse
import com.currencies.mainpackage.api.dto.response.`object`.CityObject
import com.currencies.mainpackage.core.ticket.TicketService
import com.currencies.mainpackage.entities.jpa.JpaCity
import com.currencies.mainpackage.entities.Ticket
import com.currencies.mainpackage.repositories.TicketRepository
import jakarta.persistence.EntityNotFoundException
import java.sql.Date
import java.time.Duration
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TicketServiceImpl(private val ticketRepository: TicketRepository) : TicketService {

    override fun findAll(): List<TicketResponse> {
        return ticketRepository.findAll()
            .stream()
            .map(::mapToTicketResponse)
            .toList()
    }

    override fun findById(id: Long): TicketResponse {
        return ticketRepository.findById(id)
            .map(::mapToTicketResponse)
            .orElseThrow { EntityNotFoundException("Id $id is not found") }
    }

    override fun findTickets(from: String, to: String, startFlightDate: Date): List<TicketResponse> {
        return ticketRepository.findTickets(from, to, startFlightDate)
            .stream()
            .map(::mapToTicketResponse)
            .toList()
    }

    override fun save(request: CreateTicketRequest): TicketResponse {
        val ticket = mapToTicket(request)
        return mapToTicketResponse(ticketRepository.save(ticket))
    }

    @Transactional
    override fun update(id: Long, request: CreateTicketRequest): TicketResponse {
        ticketRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Ticket $id is not found") }
        val editedTicket = updateTicket(id, request)
        return mapToTicketResponse(ticketRepository.save(editedTicket))
    }

    override fun delete(id: Long) {
        ticketRepository.deleteById(id)
    }

    private fun mapToTicketResponse(ticket: Ticket): TicketResponse {
        return TicketResponse(
            id = ticket.id!!,
            price = ticket.price,
            startFlightDate = ticket.startFlightDate,
            endFlightDate = ticket.endFlightDate,
            inFlight = ticket.inFlight,
            fromPlace = mapToCityObject(ticket.fromPlace),
            toPlace = mapToCityObject(ticket.toPlace)
        )
    }

    private fun mapToTicket(request: CreateTicketRequest): Ticket {
        val inFlight = Duration.between(
            request.startFlightDate.toInstant(),
            request.endFlightDate.toInstant()
        )

        return Ticket(
            price = request.price,
            startFlightDate = request.startFlightDate,
            endFlightDate = request.endFlightDate,
            inFlight = inFlight,
            fromPlace = mapToCity(request.fromPlace),
            toPlace = mapToCity(request.toPlace)
        )
    }

    private fun updateTicket(ticketId: Long, request: CreateTicketRequest): Ticket{
        val inFlight = Duration.between(
            request.startFlightDate.toInstant(),
            request.endFlightDate.toInstant()
        )

        return Ticket(
            id = ticketId,
            price = request.price,
            startFlightDate = request.startFlightDate,
            endFlightDate = request.endFlightDate,
            inFlight = inFlight,
            fromPlace = mapToCity(request.fromPlace),
            toPlace = mapToCity(request.toPlace)
        )
    }

    private fun mapToCityObject(jpaCity: JpaCity) = CityObject(id = jpaCity.id!!, name = jpaCity.name)

    private fun mapToCity(cityId: Long) = JpaCity(id = cityId, name = "")

}
