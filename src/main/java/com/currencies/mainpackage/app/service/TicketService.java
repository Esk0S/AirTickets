package com.currencies.mainpackage.app.service;

import com.currencies.mainpackage.models.CreateTicketRequest;
import com.currencies.mainpackage.entities.Ticket;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface TicketService {

    @NotNull
    List<Ticket> findAll();

    @NotNull
    Ticket findById(@NotNull Integer id);

    @NotNull
    List<Ticket> findTickets(String from, String to, Date startFlightDate);

    @NotNull
    Ticket createTicket(@NotNull CreateTicketRequest request);

    @NotNull
    Ticket update(@NotNull Integer id, @NotNull CreateTicketRequest request);

    void delete(@NotNull Integer id);


}
