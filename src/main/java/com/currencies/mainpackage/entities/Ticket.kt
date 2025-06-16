package com.currencies.mainpackage.entities

import com.currencies.mainpackage.entities.jpa.JpaCity
import com.impossibl.postgres.api.data.Interval
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.io.Serializable
import java.sql.Timestamp
import java.time.Duration
import org.hibernate.annotations.Type

@Entity
@Convert(attributeName = "interval", converter = Interval::class)
data class Ticket (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val price: Int,

    val startFlightDate: Timestamp,

    val endFlightDate: Timestamp,

    @Type(PostgreSQLIntervalType::class)
    @Column(columnDefinition = "interval")
    val inFlight: Duration,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_place", referencedColumnName = "id")
    val fromPlace: JpaCity,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_place", referencedColumnName = "id")
    val toPlace: JpaCity

) : Serializable
