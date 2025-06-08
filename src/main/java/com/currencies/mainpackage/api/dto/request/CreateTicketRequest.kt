package com.currencies.mainpackage.api.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED
import java.io.Serializable
import java.sql.Timestamp

data class CreateTicketRequest (

    @Schema(
        description = "Цена билета",
        requiredMode = REQUIRED,
        example = "123"
    )
    val price: Int,

    @Schema(
        description = "Дата начала полёта",
        requiredMode = REQUIRED
    )
    val startFlightDate: Timestamp,

    @Schema(
        description = "Дата окончания полёта",
        requiredMode = REQUIRED
    )
    val endFlightDate: Timestamp,

    @Schema(
        description = "ID из какого города",
        requiredMode = REQUIRED,
        example = "1"
    )
    val fromPlace: Long,

    @Schema(
        description = "ID в какой город",
        requiredMode = REQUIRED,
        example = "2"
    )
    val toPlace: Long

) : Serializable
