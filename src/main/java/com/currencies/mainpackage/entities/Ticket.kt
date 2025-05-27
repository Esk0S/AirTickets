package com.currencies.mainpackage.entities;

import com.impossibl.postgres.api.data.Interval;
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.time.Duration;

@Entity
@Data
@Convert(attributeName = "interval", converter = Interval.class)
@Accessors(chain = true)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer price;

    private Timestamp startFlightDate;

    private Timestamp endFlightDate;

    @Type(PostgreSQLIntervalType.class)
    @Column(columnDefinition = "interval")
    private Duration inFlight;

    private String fromPlace;

    private String toPlace;

}
