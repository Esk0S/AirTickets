package com.currencies.mainpackage.api.controllers.html

import com.currencies.mainpackage.api.ApiPath
import com.currencies.mainpackage.api.ApiPath.ACCOUNT
import com.currencies.mainpackage.api.ApiPath.TICKET_DETAILS
import com.currencies.mainpackage.api.HtmlPath
import com.currencies.mainpackage.core.security.UserPrincipal
import com.currencies.mainpackage.core.ticket.TicketService
import com.currencies.mainpackage.core.ticketpurchase.TicketPurchaseService
import java.time.format.DateTimeFormatter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AccountHtmlController(private val ticketPurchaseService: TicketPurchaseService) {

    @GetMapping(ApiPath.ACCOUNT)
    fun profile(
        model: Model,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): String {
        val purchases = ticketPurchaseService.getAllByUserId(userPrincipal.id)
        model.addAttribute("user", userPrincipal.user)
        model.addAttribute("purchases", purchases)
        model.addAttribute("isPurchaseButtonShown", false)
        model.addAttribute("ticketDetailsUrl", ACCOUNT + TICKET_DETAILS)

        return HtmlPath.ACCOUNT
    }

}