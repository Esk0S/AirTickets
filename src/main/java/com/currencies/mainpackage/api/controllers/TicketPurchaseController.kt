package com.currencies.mainpackage.api.controllers

import com.currencies.mainpackage.api.ApiPath.ID
import com.currencies.mainpackage.api.ApiPath.PURCHASE
import com.currencies.mainpackage.api.HtmlPath.ACCOUNT
import com.currencies.mainpackage.api.dto.request.TicketPurchaseRequest
import com.currencies.mainpackage.core.security.UserPrincipal
import com.currencies.mainpackage.core.ticketpurchase.TicketPurchaseService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping
class TicketPurchaseController(private val ticketPurchaseService: TicketPurchaseService) {

    @PostMapping(PURCHASE + ID)
    fun purchase(
        @ModelAttribute ticketPurchaseRequest: TicketPurchaseRequest,
        @AuthenticationPrincipal user: UserPrincipal?,
        @PathVariable("id") ticketId: Long
    ): String {
        ticketPurchaseService.purchase(ticketId, user, ticketPurchaseRequest)

        return "redirect:/$ACCOUNT"
    }

}