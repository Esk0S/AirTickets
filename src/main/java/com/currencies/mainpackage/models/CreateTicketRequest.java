package com.currencies.mainpackage.models;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Data
public class CreateTicketRequest {
    private String id;
    private Integer price;
    private Timestamp startFlightDate;
    private Timestamp endFlightDate;
    private String fromPlace;
    private String toPlace;
//    private Time inFlight;
}
