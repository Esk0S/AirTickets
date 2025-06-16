package com.currencies.mainpackage.api.dto.response

import com.currencies.mainpackage.api.dto.response.`object`.TicketObject
import com.currencies.mainpackage.api.dto.response.`object`.UserObject
import java.time.LocalDate

data class TicketPurchaseResponse(

    val id: Long,

    val user: UserObject?,

    val ticket: TicketObject,

    val firstName: String,

    val lastName: String,

    val middleName: String?,

    val birthDate: LocalDate,

    val passportNumber: String,

    val withBaggage: Boolean

)