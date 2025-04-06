package com.currencies.mainpackage.core.ticket;

import com.currencies.mainpackage.api.dto.response.TicketResponse;
import com.currencies.mainpackage.api.dto.request.CreateTicketRequest;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.List;

public interface TicketService {

    @NotNull
    List<TicketResponse> findAll();

    @NotNull
    TicketResponse findById(@NotNull Integer id);

    @NotNull
    List<TicketResponse> findTickets(String from, String to, Date startFlightDate);

    @NotNull
    TicketResponse createTicket(@NotNull CreateTicketRequest request);

    @NotNull
    TicketResponse update(@NotNull Integer id, @NotNull CreateTicketRequest request);

    void delete(@NotNull Integer id);


}
