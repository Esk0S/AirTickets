package com.currencies.mainpackage.entities

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.io.Serializable
import java.time.LocalDate

@Entity
data class Purchase(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    val ticket: Ticket,

    val firstName: String,

    val lastName: String,

    val middleName: String?,

    val birthDate: LocalDate,

    val passportNumber: String,

    val withBaggage: Boolean

) : Serializable
