package com.currencies.mainpackage.service;

import com.currencies.mainpackage.TicketRepository;
import com.currencies.mainpackage.entities.Ticket;
import com.currencies.mainpackage.models.CreateTicketRequest;
import com.currencies.mainpackage.models.TicketResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @NotNull
    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> findAll() {
        return ticketRepository.findAll()
                .stream()
                .map(this::buildTicketResponse)
                .collect(Collectors.toList());
    }

    @NotNull
    private TicketResponse buildTicketResponse(@NotNull Ticket ticket) {
        return new TicketResponse()
                .setId(ticket.getId())
                .setPrice(ticket.getPrice())
                .setInFlight(ticket.getInFlight())
                .setStartFlightDate(ticket.getStartFlightDate())
//                .setStartFlightTime(ticket.getStartFlightTime())
                .setEndFlightDate(ticket.getEndFlightDate())
//                .setEndFlightTime(ticket.getEndFlightTime())
                .setFromPlace(ticket.getFromPlace())
                .setToPlace(ticket.getToPlace());
    }

    @NotNull
    @Override
    @Transactional(readOnly = true)
    public TicketResponse findById(@NotNull String id) {
        return ticketRepository.findById(id)
                .map(this::buildTicketResponse)
                .orElseThrow(() -> new EntityNotFoundException("Id " + id + " is not found"));
    }

//    @Override
//    public List<TicketResponse> findByFromFromTo(@NotNull String fromPlace, @NotNull String toPlace) {
//        return ticketRepository.findByFromFromTo(fromPlace, toPlace)
//                .map(this::buildTicketResponse)
//                .orElseThrow(() -> new EntityNotFoundException("Id" + id + " is not found"));
//    }

    @NotNull
    @Override
    @Transactional
    public TicketResponse createTicket(@NotNull CreateTicketRequest request) {
        Ticket ticket = buildTicketRequest(request);
        return buildTicketResponse(ticketRepository.save(ticket));
    }

    @NotNull
    private Ticket buildTicketRequest(@NotNull CreateTicketRequest request) {
//        Time inFlightTime = new Time(request.getEndFlightDate().getTime()
//                - request.getStartFlightDate().getTime());
        LocalTime inFlightTime = LocalTime.ofSecondOfDay((request.getEndFlightDate().getTime()
                - request.getStartFlightDate().getTime()) / 1000);
        return new Ticket()
                .setPrice(request.getPrice())
                .setInFlight(inFlightTime)
                .setStartFlightDate(request.getStartFlightDate())
//                .setStartFlightTime(request.getStartFlightTime())
                .setEndFlightDate(request.getEndFlightDate())
//                .setEndFlightTime(request.getEndFlightTime())
                .setFromPlace(request.getFromPlace())
                .setToPlace(request.getToPlace());
    }

    @NotNull
    @Override
    @Transactional
    public TicketResponse update(@NotNull String id, @NotNull CreateTicketRequest request) {
        Ticket ticket =  ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket " + id + " is not found"));
        ticketUpdate(ticket, request);
        return buildTicketResponse(ticketRepository.save(ticket));
    }

    private void ticketUpdate(@NotNull Ticket ticket, @NotNull CreateTicketRequest request) {
        ofNullable(request.getPrice()).map(ticket::setPrice);
//        Time inFlightTime = new Time(request.getEndFlightDate().getTime()
//                - request.getStartFlightDate().getTime());
        LocalTime inFlightTime = LocalTime.ofSecondOfDay((request.getEndFlightDate().getTime()
                - request.getStartFlightDate().getTime()) / 1000);
        Optional.of(inFlightTime).map(ticket::setInFlight);
        ofNullable(request.getStartFlightDate()).map(ticket::setStartFlightDate);
        ofNullable(request.getEndFlightDate()).map(ticket::setEndFlightDate);
        ofNullable(request.getFromPlace()).map(ticket::setFromPlace);
        ofNullable(request.getToPlace()).map(ticket::setToPlace);
    }

    @Override
    @Transactional
    public void delete(@NotNull String id) {
        ticketRepository.deleteById(id);
    }
}
