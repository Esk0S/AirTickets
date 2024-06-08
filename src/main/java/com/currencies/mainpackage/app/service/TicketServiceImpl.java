package com.currencies.mainpackage.app.service;

import com.currencies.mainpackage.repositories.TicketRepository;
import com.currencies.mainpackage.models.CreateTicketRequest;
import com.currencies.mainpackage.entities.Ticket;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @NotNull
    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findAll() {
        return ticketRepository.findAll()
                .stream()
                .map(this::buildTicketResponse)
                .toList();
    }

    @NotNull
    private Ticket buildTicketResponse(@NotNull Ticket ticket) {
        return new Ticket()
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
    public Ticket findById(@NotNull Integer id) {
        return ticketRepository.findById(id)
                .map(this::buildTicketResponse)
                .orElseThrow(() -> new EntityNotFoundException("Id " + id + " is not found"));
    }

    @NotNull
    public List<Ticket> findTickets(String from, String to, Date startFlightDate) {
        return ticketRepository.findTickets(from, to, startFlightDate)
                .stream()
                .map(this::buildTicketResponse)
                .toList();
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
    public Ticket createTicket(@NotNull CreateTicketRequest request) {
        Ticket ticket = buildTicketRequest(request);
        return buildTicketResponse(ticketRepository.save(ticket));
    }

    @NotNull
    private Ticket buildTicketRequest(@NotNull CreateTicketRequest request) {
//        Time inFlightTime = new Time(request.getEndFlightDate().getTime()
//                - request.getStartFlightDate().getTime());

        Duration inFlightTime = Duration.between(request.getStartFlightDate().toInstant(),
                request.getEndFlightDate().toInstant());
//        PGInterval interval = new PGInterval((int)inFlightTime.toDaysPart(), 0, 0, inFlightTime.toHoursPart(), inFlightTime.toMinutesPart(), inFlightTime.toSecondsPart());
//        PGInterval interval = null;
//        try {
//            interval = new PGInterval(inFlightTime.toHoursPart() + ":" + inFlightTime.toMinutesPart() + ":" + inFlightTime.toSecondsPart());
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        Interval interval = Interval.of(inFlightTime);

//        LocalTime inFlightTime = LocalTime.ofSecondOfDay((request.getEndFlightDate().getTime()
//                - request.getStartFlightDate().getTime()) / 1000);
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
    public Ticket update(@NotNull Integer id, @NotNull CreateTicketRequest request) {
        Ticket ticket =  ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket " + id + " is not found"));
        ticketUpdate(ticket, request);
        return buildTicketResponse(ticketRepository.save(ticket));
    }

    private void ticketUpdate(@NotNull Ticket ticket, @NotNull CreateTicketRequest request) {
        ofNullable(request.getPrice()).map(ticket::setPrice);
//        Time inFlightTime = new Time(request.getEndFlightDate().getTime()
//                - request.getStartFlightDate().getTime());
//        LocalTime inFlightTime = LocalTime.ofSecondOfDay((request.getEndFlightDate().getTime()
//                - request.getStartFlightDate().getTime()) / 1000);
        Duration inFlightTime = Duration.between(request.getStartFlightDate().toInstant(),
                request.getEndFlightDate().toInstant());
//        Interval interval = Interval.of(inFlightTime);
//        PGInterval pgi = null;
//        try {
//            pgi = new PGInterval(inFlightTime.toHoursPart() + ":" + inFlightTime.toMinutesPart() + ":" + inFlightTime.toSecondsPart());
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        final int years = pgi.getYears();
//        final int months = pgi.getMonths();
//        final int days = pgi.getDays();
//        final int hours = pgi.getHours();
//        final int mins = pgi.getMinutes();
//        final double secs = pgi.getSeconds();

//        Period period =  new Period(years, months, 0, days, hours, mins, (int)secs, 0);
        Optional.of(inFlightTime).map(ticket::setInFlight);
        ofNullable(request.getStartFlightDate()).map(ticket::setStartFlightDate);
        ofNullable(request.getEndFlightDate()).map(ticket::setEndFlightDate);
        ofNullable(request.getFromPlace()).map(ticket::setFromPlace);
        ofNullable(request.getToPlace()).map(ticket::setToPlace);
    }

    @Override
    @Transactional
    public void delete(@NotNull Integer id) {
        ticketRepository.deleteById(id);
    }
}
