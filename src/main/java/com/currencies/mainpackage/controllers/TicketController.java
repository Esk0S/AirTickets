package com.currencies.mainpackage.controllers;

import com.currencies.mainpackage.models.CreateTicketRequest;
import com.currencies.mainpackage.models.TicketResponse;
import com.currencies.mainpackage.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

import static org.springframework.http.MediaType.*;

@Controller
@RequestMapping("/admin/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @ResponseBody
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<TicketResponse> findAll() {
        return ticketService.findAll();
    }

    @GetMapping(value = "/identifiers/{id}", produces = APPLICATION_JSON_VALUE)
    public TicketResponse findById(@PathVariable String id) {
        return ticketService.findById(id);
    }

    @GetMapping(value = "/cities")
    public String findByFromAndFromTo(@RequestParam String fromPlace, @RequestParam String toPlace, Model model) {
//        return ticketService.findByFromFromTo(fromPlace, toPlace);
//        return "From City: " + fromPlace + " To City: " + toPlace;
        model.addAttribute("fromPlace", fromPlace)
                .addAttribute("toPlace", toPlace);
//        return model.toString();
        return "index";
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public TicketResponse create(@RequestBody CreateTicketRequest request) {
        return ticketService.createTicket(request);
    }

    @PatchMapping(value = "/identifiers/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public TicketResponse update(@PathVariable String id, @RequestBody CreateTicketRequest request) {
        return ticketService.update(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/identifiers/{id}", produces = APPLICATION_JSON_VALUE)
    public void delete(@PathVariable String id) {
        ticketService.delete(id);
    }

}
