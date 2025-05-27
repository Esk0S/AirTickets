package com.currencies.mainpackage.core.city

import com.currencies.mainpackage.api.dto.request.CreateCityRequest
import com.currencies.mainpackage.api.dto.request.CreateTicketRequest
import com.currencies.mainpackage.api.dto.response.CitiesListResponse
import com.currencies.mainpackage.api.dto.response.CityResponse
import com.currencies.mainpackage.api.dto.response.TicketResponse
import java.sql.Date

interface CityService {

    fun findAll(): List<CityResponse>

    fun findById(id: Long): CityResponse?

//    fun findByName(name: String): CitiesListResponse

    fun save(request: CreateCityRequest): CityResponse

    fun update(id: Long, request: CreateCityRequest): CityResponse

    fun delete(id: Long)

}
