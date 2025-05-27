package com.currencies.mainpackage.api.controllers;

import com.currencies.mainpackage.api.dto.request.CreateTicketRequest;
import com.currencies.mainpackage.api.dto.response.TicketResponse;
import com.currencies.mainpackage.core.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

import static com.currencies.mainpackage.api.ApiPath.ID;
import static com.currencies.mainpackage.api.ApiPath.SEARCH;
import static com.currencies.mainpackage.api.ApiPath.TICKETS;

@RestController
@CrossOrigin
@RequestMapping(TICKETS)
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public List<TicketResponse> findAll() {
        return ticketService.findAll();
    }

    @GetMapping(ID)
    public TicketResponse findById(@PathVariable Integer id) {
        return ticketService.findById(id);
    }

    @GetMapping(SEARCH)
    public List<TicketResponse> findByFromAndFromTo(@RequestParam String fromPlace,
                                                    @RequestParam String toPlace,
                                                    @RequestParam Date when) {
        return ticketService.findTickets(fromPlace, toPlace, when);
    }

    @PostMapping
    public TicketResponse create(@RequestBody CreateTicketRequest request) {
        return ticketService.createTicket(request);
    }

    @PatchMapping(ID)
    public TicketResponse update(@PathVariable Integer id, @RequestBody CreateTicketRequest request) {
        return ticketService.update(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(ID)
    public void delete(@PathVariable Integer id) {
        ticketService.delete(id);
    }

}
