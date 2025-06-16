package com.currencies.mainpackage.api.dto.response.`object`

import java.sql.Timestamp
import java.time.Duration

data class TicketObject(

    val id: Long,

    val price: Int,

    val startFlightDate: Timestamp,

    val endFlightDate: Timestamp,

    val inFlight: Duration,

    val fromPlace: CityObject?,

    val toPlace: CityObject?

)
