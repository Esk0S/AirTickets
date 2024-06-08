package com.currencies.mainpackage.api.controllers;

import com.currencies.mainpackage.models.CreateTicketRequest;
import com.currencies.mainpackage.entities.Ticket;
import com.currencies.mainpackage.app.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Ticket> findAll() {
        return ticketService.findAll();
    }

    @GetMapping(value = "/identifiers/{id}", produces = APPLICATION_JSON_VALUE)
    public Ticket findById(@PathVariable Integer id) {
        return ticketService.findById(id);
    }

    @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    public List<Ticket> findByFromAndFromTo(@RequestParam String fromPlace,
                                                    @RequestParam String toPlace,
                                                    @RequestParam Date when,
                                                    Model model) {
//        model.addAttribute("fromPlace", fromPlace)
//                .addAttribute("toPlace", toPlace)
//                .addAttribute("when", when);
        return ticketService.findTickets(fromPlace, toPlace, when);
    }

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Ticket create(@RequestBody CreateTicketRequest request) {
        return ticketService.createTicket(request);
    }

    @PatchMapping(value = "/identifiers/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Ticket update(@PathVariable Integer id, @RequestBody CreateTicketRequest request) {
        return ticketService.update(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/identifiers/{id}", produces = APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Integer id) {
        ticketService.delete(id);
    }

}
