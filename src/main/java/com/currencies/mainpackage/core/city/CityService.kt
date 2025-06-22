package com.currencies.mainpackage.core.city

import com.currencies.mainpackage.api.dto.request.CreateCityRequest
import com.currencies.mainpackage.api.dto.response.CityResponse
import org.springframework.data.domain.Page

interface CityService {

    fun reindexAll()

    fun findAll(): List<CityResponse>

    fun findById(id: Long): CityResponse?

    fun findByName(name: String, page: Int, size: Int): List<CityResponse>

    fun saveJpa(request: CreateCityRequest): CityResponse

    fun save(request: CreateCityRequest): CityResponse

    fun update(id: Long, request: CreateCityRequest): CityResponse

    fun delete(id: Long)

    fun deleteEs(id: Long)

    fun deleteAllEs()

}
