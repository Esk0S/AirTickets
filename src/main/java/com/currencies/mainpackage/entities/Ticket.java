package com.currencies.mainpackage.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "tickets")
@Accessors(chain = true)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Integer price;
    @Column(columnDefinition = "timestamp")
    private Timestamp startFlightDate;
    @Column(columnDefinition = "timestamp")
    private Timestamp endFlightDate;
    private LocalTime inFlight;
    private String fromPlace;
    private String toPlace;

}
