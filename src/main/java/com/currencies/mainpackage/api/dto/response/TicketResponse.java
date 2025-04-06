package com.currencies.mainpackage.api.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.time.Duration;

@Data
@Accessors(chain = true)
public class TicketResponse {

    private Integer id;

    private Integer price;

    private Timestamp startFlightDate;

    private Timestamp endFlightDate;

    private Duration inFlight;

    private String fromPlace;

    private String toPlace;

}