package com.currencies.mainpackage.api.dto.request

import java.io.Serializable
import java.time.LocalDate

data class TicketPurchaseRequest(

    val firstName: String,

    val lastName: String,

    val middleName: String?,

    val passportNumber: String,

    val birthDate: LocalDate,

    val withBaggage: Boolean?

) : Serializable
