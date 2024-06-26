package com.currencies.mainpackage.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CreateTicketRequest {
    private Integer id;
    private Integer price;
    private Timestamp startFlightDate;
    private Timestamp endFlightDate;
    private String fromPlace;
    private String toPlace;
//    private Time inFlight;
}
