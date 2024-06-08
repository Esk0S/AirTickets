package com.currencies.mainpackage.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.time.LocalTime;

@Data
@Accessors(chain = true)
public class TicketResponse {
    private String id;
    private Integer price;
    private Timestamp startFlightDate;
    private Timestamp endFlightDate;
    private LocalTime inFlight;
    private String fromPlace;
    private String toPlace;
}