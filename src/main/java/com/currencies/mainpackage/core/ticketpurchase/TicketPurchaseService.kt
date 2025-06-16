package com.currencies.mainpackage.core.ticketpurchase

import com.currencies.mainpackage.api.dto.request.TicketPurchaseRequest
import com.currencies.mainpackage.api.dto.response.TicketPurchaseResponse
import com.currencies.mainpackage.api.dto.response.TicketResponse
import com.currencies.mainpackage.core.security.UserPrincipal
import com.currencies.mainpackage.entities.Purchase

interface TicketPurchaseService {

    fun purchase(
        ticketId: Long,
        userPrincipal: UserPrincipal?,
        ticketPurchaseRequest: TicketPurchaseRequest
    ): TicketPurchaseResponse

    fun getTicketsByUserId(userId: Long) : List<TicketResponse>

    fun getAllByUserId(userId: Long) : List<Purchase>

}