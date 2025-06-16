package com.currencies.mainpackage.api.controllers

import com.currencies.mainpackage.api.ApiPath.ACCOUNT
import com.currencies.mainpackage.api.ApiPath.ID
import com.currencies.mainpackage.api.ApiPath.TICKET_DETAILS
import com.currencies.mainpackage.core.ticket.TicketService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class AccountController(private val ticketService: TicketService) {

    @GetMapping(ACCOUNT + TICKET_DETAILS + ID)
    fun ticketDetails(
        @PathVariable("id") ticketId: Long,
        model: Model,
    ): String {
        val ticket = ticketService.findById(ticketId)
        model.addAttribute("ticketRows", listOf(ticket))

        return "fragments/ticketDetails :: ticketDetails"
    }

}