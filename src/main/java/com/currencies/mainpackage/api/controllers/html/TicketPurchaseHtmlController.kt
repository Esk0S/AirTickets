package com.currencies.mainpackage.api.controllers.html

import com.currencies.mainpackage.api.ApiPath.ID
import com.currencies.mainpackage.api.ApiPath.PURCHASE
import com.currencies.mainpackage.api.HtmlPath.TICKET_PURCHASE
import com.currencies.mainpackage.core.security.UserPrincipal
import com.currencies.mainpackage.core.ticket.TicketService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class TicketPurchaseHtmlController(private val ticketService: TicketService) {

    @GetMapping(PURCHASE + ID)
    fun purchasePage(
        @PathVariable("id") ticketId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        model: Model
    ): String {
        val ticket = ticketService.findById(ticketId)
        model.addAttribute("purchaseUrl", PURCHASE)
        model.addAttribute("ticket", ticket)
        model.addAttribute("authUser", userPrincipal?.user)

        return TICKET_PURCHASE
    }

}