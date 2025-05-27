package com.currencies.mainpackage.core.ticket.implementation;

import com.currencies.mainpackage.api.dto.response.TicketResponse;
import com.currencies.mainpackage.core.ticket.TicketService;
import com.currencies.mainpackage.entities.Ticket;
import com.currencies.mainpackage.api.dto.request.CreateTicketRequest;
import com.currencies.mainpackage.repositories.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Duration;
import java.util.List;

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
                .map(this::mapToTicketResponse)
                .toList();
    }

    @NotNull
    @Override
    @Transactional(readOnly = true)
    public TicketResponse findById(@NotNull Integer id) {
        return ticketRepository.findById(id)
                .map(this::mapToTicketResponse)
                .orElseThrow(() -> new EntityNotFoundException("Id " + id + " is not found"));
    }

    @NotNull
    public List<TicketResponse> findTickets(String from, String to, Date startFlightDate) {
        return ticketRepository.findTickets(from, to, startFlightDate)
                .stream()
                .map(this::mapToTicketResponse)
                .toList();
    }

    @NotNull
    @Override
    @Transactional
    public TicketResponse createTicket(@NotNull CreateTicketRequest request) {
        Ticket ticket = mapToTicket(request);
        return mapToTicketResponse(ticketRepository.save(ticket));
    }

    @NotNull
    private Ticket mapToTicket(@NotNull CreateTicketRequest request) {
        Duration inFlightTime = Duration.between(request.getStartFlightDate().toInstant(),
                request.getEndFlightDate().toInstant());
        return new Ticket()
                .setPrice(request.getPrice())
                .setStartFlightDate(request.getStartFlightDate())
                .setEndFlightDate(request.getEndFlightDate())
                .setInFlight(inFlightTime)
                .setFromPlace(request.getFromPlace())
                .setToPlace(request.getToPlace());
    }

    @NotNull
    @Override
    @Transactional
    public TicketResponse update(@NotNull Integer id, @NotNull CreateTicketRequest request) {
        Ticket ticket =  ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket " + id + " is not found"));
        ticketUpdate(ticket, request);
        return mapToTicketResponse(ticketRepository.save(ticket));
    }

    private void ticketUpdate(@NotNull Ticket ticket, @NotNull CreateTicketRequest request) {
        ofNullable(request.getPrice()).ifPresent(ticket::setPrice);
        if (request.getStartFlightDate() != null && request.getEndFlightDate() != null) {
            Duration inFlightTime = Duration.between(
                    request.getStartFlightDate().toInstant(),
                    request.getEndFlightDate().toInstant()
            );
            ticket.setInFlight(inFlightTime);
        }
        ofNullable(request.getStartFlightDate()).ifPresent(ticket::setStartFlightDate);
        ofNullable(request.getEndFlightDate()).ifPresent(ticket::setEndFlightDate);
        ofNullable(request.getFromPlace()).ifPresent(ticket::setFromPlace);
        ofNullable(request.getToPlace()).ifPresent(ticket::setToPlace);
    }

    @Override
    @Transactional
    public void delete(@NotNull Integer id) {
        ticketRepository.deleteById(id);
    }

    private TicketResponse mapToTicketResponse(@NotNull Ticket ticket) {
        return new TicketResponse()
                .setId(ticket.getId())
                .setPrice(ticket.getPrice())
                .setStartFlightDate(ticket.getStartFlightDate())
                .setEndFlightDate(ticket.getEndFlightDate())
                .setInFlight(ticket.getInFlight())
                .setFromPlace(ticket.getFromPlace())
                .setToPlace(ticket.getToPlace());
    }

}
