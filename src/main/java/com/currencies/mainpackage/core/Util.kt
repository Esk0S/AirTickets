package com.currencies.mainpackage.core

import com.currencies.mainpackage.api.dto.response.TicketResponse
import com.currencies.mainpackage.api.dto.response.`object`.CityObject
import com.currencies.mainpackage.entities.Ticket
import com.currencies.mainpackage.entities.jpa.JpaCity

fun mapToTicketResponse(ticket: Ticket): TicketResponse {
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

private fun mapToCityObject(jpaCity: JpaCity) = CityObject(id = jpaCity.id!!, name = jpaCity.name)
