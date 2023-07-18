package com.currencies.mainpackage.models;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.ZonedDateTime;

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
