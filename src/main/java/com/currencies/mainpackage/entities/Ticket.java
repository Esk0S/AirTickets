package com.currencies.mainpackage.entities;

import com.impossibl.postgres.api.data.Interval;
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.time.Duration;

@Entity
@Data
@Convert(attributeName = "interval", converter = Interval.class)
@Table(name = "tickets")
@Accessors(chain = true)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer price;
//    @Column(columnDefinition = "timestamp")
    private Timestamp startFlightDate;
//    @Column(columnDefinition = "timestamp")
    private Timestamp endFlightDate;

    @Type(PostgreSQLIntervalType.class)
    @Column(columnDefinition = "interval")
    private Duration inFlight;

    private String fromPlace;
    private String toPlace;

}
