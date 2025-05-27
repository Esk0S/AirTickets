package com.currencies.mainpackage.api.dto.response

import com.currencies.mainpackage.api.dto.response.`object`.CityObject
import java.io.Serializable
import java.sql.Timestamp
import java.time.Duration

data class TicketResponse (
    val id: Long,

    val price: Int,

    val startFlightDate: Timestamp,

    val endFlightDate: Timestamp,

    val inFlight: Duration,

    val fromPlace: String,

    val toPlace: String

) : Serializable