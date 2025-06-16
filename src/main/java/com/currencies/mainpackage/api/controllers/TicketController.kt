package com.currencies.mainpackage.api.controllers

import com.currencies.mainpackage.api.ApiPath.ID
import com.currencies.mainpackage.api.ApiPath.PURCHASE
import com.currencies.mainpackage.api.ApiPath.SEARCH
import com.currencies.mainpackage.api.ApiPath.TICKETS
import com.currencies.mainpackage.api.docs.SwaggerTicketController
import com.currencies.mainpackage.api.dto.request.CreateTicketRequest
import com.currencies.mainpackage.api.dto.response.TicketResponse
import com.currencies.mainpackage.core.ticket.TicketService
import com.currencies.mainpackage.entities.Ticket
import java.sql.Date
import java.time.Duration
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@CrossOrigin
@RequestMapping(TICKETS)
class TicketController(private val ticketService: TicketService) : SwaggerTicketController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    override fun findAll(): ResponseEntity<List<TicketResponse>> {
        val tickets = ticketService.findAll()
        return ResponseEntity.ok().body(tickets)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(ID)
    override fun findById(@PathVariable id: Long): ResponseEntity<TicketResponse> {
        return ResponseEntity.ok().body(ticketService.findById(id))
    }

    @GetMapping(SEARCH)
    fun findByFromAndTo(
        @RequestParam fromPlace: String,
        @RequestParam toPlace: String,
        @RequestParam `when`: Date,
        model: Model
    ): String {
        val ticketRows = ticketService.findTickets(fromPlace, toPlace, `when`)
        model.addAttribute("ticketRows", ticketRows)
        model.addAttribute("ticketPurchaseUrl", PURCHASE)
        model.addAttribute("isPurchaseButtonShown", true)

        return "fragments/ticketsList :: ticketsTable"
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    override fun create(@RequestBody request: CreateTicketRequest): ResponseEntity<TicketResponse> {
        return ResponseEntity.ok().body(ticketService.save(request))
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(ID)
    override fun update(@PathVariable id: Long, @RequestBody request: CreateTicketRequest): ResponseEntity<TicketResponse> {
        return ResponseEntity.ok().body(ticketService.update(id, request))
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(ID)
    override fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        ticketService.delete(id)
        return ResponseEntity.noContent().build()
    }

}
