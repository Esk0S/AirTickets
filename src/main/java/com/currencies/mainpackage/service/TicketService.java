package com.currencies.mainpackage.service;

import com.currencies.mainpackage.models.CreateTicketRequest;
import com.currencies.mainpackage.models.TicketResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TicketService {

    @NotNull
    List<TicketResponse> findAll();

    @NotNull
    TicketResponse findById(@NotNull String id);

//    List<TicketResponse> findByFromFromTo(@NotNull String fromPlace, @NotNull String toPlace);

    @NotNull
    TicketResponse createTicket(@NotNull CreateTicketRequest request);

    @NotNull
    TicketResponse update(@NotNull String id, @NotNull CreateTicketRequest request);

    void delete(@NotNull String id);


}
