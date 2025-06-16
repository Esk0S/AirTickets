package com.currencies.mainpackage.core.ticketpurchase.implementation

import com.currencies.mainpackage.api.dto.request.TicketPurchaseRequest
import com.currencies.mainpackage.api.dto.response.TicketPurchaseResponse
import com.currencies.mainpackage.api.dto.response.TicketResponse
import com.currencies.mainpackage.api.dto.response.`object`.CityObject
import com.currencies.mainpackage.api.dto.response.`object`.RoleObject
import com.currencies.mainpackage.api.dto.response.`object`.TicketObject
import com.currencies.mainpackage.api.dto.response.`object`.UserObject
import com.currencies.mainpackage.core.exception.NotFoundException
import com.currencies.mainpackage.core.mapToTicketResponse
import com.currencies.mainpackage.core.security.UserPrincipal
import com.currencies.mainpackage.core.ticketpurchase.TicketPurchaseService
import com.currencies.mainpackage.entities.Purchase
import com.currencies.mainpackage.entities.Role
import com.currencies.mainpackage.entities.Ticket
import com.currencies.mainpackage.entities.User
import com.currencies.mainpackage.entities.jpa.JpaCity
import com.currencies.mainpackage.repositories.PurchaseRepository
import com.currencies.mainpackage.repositories.TicketRepository
import org.springframework.stereotype.Service

@Service
class TicketPurchaseServiceImpl(
    private val purchaseRepository: PurchaseRepository,
    private val ticketRepository: TicketRepository
) : TicketPurchaseService {

    override fun purchase(
        ticketId: Long,
        userPrincipal: UserPrincipal?,
        ticketPurchaseRequest: TicketPurchaseRequest
    ): TicketPurchaseResponse {
        val user = userPrincipal?.user
        val ticket = ticketRepository.findByIdOrNull(ticketId)
            ?: throw NotFoundException("Билет с id `$ticketId` не найден.")
        val purchasedTicket =  purchaseRepository.save(
            Purchase(
                user = user,
                ticket = ticket,
                firstName = ticketPurchaseRequest.firstName,
                lastName = ticketPurchaseRequest.lastName,
                middleName = ticketPurchaseRequest.middleName,
                birthDate = ticketPurchaseRequest.birthDate,
                passportNumber = ticketPurchaseRequest.passportNumber.replace(Regex("\\s+"), ""),
                withBaggage = ticketPurchaseRequest.withBaggage ?: false
            )
        )

        return mapToTicketPurchaseResponse(purchasedTicket)
    }

    override fun getTicketsByUserId(userId: Long) : List<TicketResponse> {
        return purchaseRepository.findTicketsByUserId(userId).map(::mapToTicketResponse)
    }

    override fun getAllByUserId(userId: Long) : List<Purchase> {
        return purchaseRepository.findAllByUserId(userId) //.map(::mapToTicketPurchaseResponse)
    }

    private fun mapToTicketPurchaseResponse(purchase: Purchase) = TicketPurchaseResponse(
        id = purchase.id!!,
        user = mapToUserObject(purchase.user),
        ticket = mapToTicketObject(purchase.ticket),
        firstName = purchase.firstName,
        lastName = purchase.lastName,
        middleName = purchase.middleName,
        birthDate = purchase.birthDate,
        passportNumber = purchase.passportNumber,
        withBaggage = purchase.withBaggage
    )

    private fun mapToUserObject(user: User?) = user?.let {
        UserObject(
            id = user.id!!,
            roles = null,
            username = user.username,
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            middleName = user.middleName
        )
    }

    private fun mapToRoleObjects(roles: Set<Role>) = roles.map {
        RoleObject(it.id!!, it.name)
    }.toSet()

    private fun mapToTicketObject(ticket: Ticket) = TicketObject(
        id = ticket.id!!,
        price = ticket.price,
        startFlightDate = ticket.startFlightDate,
        endFlightDate = ticket.endFlightDate,
        inFlight = ticket.inFlight,
        fromPlace = null,
        toPlace = null
    )

    private fun mapToCityObject(city: JpaCity) = CityObject(
        id = city.id!!,
        name = city.name,
    )

}