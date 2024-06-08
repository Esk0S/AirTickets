package com.currencies.mainpackage.app.mapper;

import com.currencies.mainpackage.entities.Ticket;
import com.currencies.mainpackage.models.CreateTicketRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

//public class Mapper {
//    @NotNull
//    @Override
//    @Transactional
//    public TicketResponse createTicket(@NotNull CreateTicketRequest request) {
//        Ticket ticket = buildTicketRequest(request);
//        return buildTicketResponse(ticketRepository.save(ticket));
//    }
//
//    @NotNull
//    private TicketResponse buildTicketResponse(@NotNull Ticket ticket) {
//        return new TicketResponse()
//                .setId(ticket.getId())
//                .setPrice(ticket.getPrice())
//                .setInFlight(ticket.getInFlight())
//                .setStartFlightDate(ticket.getStartFlightDate())
////                .setStartFlightTime(ticket.getStartFlightTime())
//                .setEndFlightDate(ticket.getEndFlightDate())
////                .setEndFlightTime(ticket.getEndFlightTime())
//                .setFromPlace(ticket.getFromPlace())
//                .setToPlace(ticket.getToPlace());
//    }
//}
