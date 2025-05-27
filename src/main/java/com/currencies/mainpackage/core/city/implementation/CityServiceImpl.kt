package com.currencies.mainpackage.core.city.implementation

import com.currencies.mainpackage.api.dto.request.CreateCityRequest
import com.currencies.mainpackage.api.dto.response.CitiesListResponse
import com.currencies.mainpackage.api.dto.response.CityResponse
import com.currencies.mainpackage.api.dto.response.`object`.CityObject
import com.currencies.mainpackage.core.city.CityService
import com.currencies.mainpackage.core.exception.NotFoundException
import com.currencies.mainpackage.entities.jpa.JpaCity
import com.currencies.mainpackage.repositories.jpa.JpaCityRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CityServiceImpl(
    private val jpaCityRepository: JpaCityRepository,
//    private val esCityRepository: EsCityRepository
) : CityService {

    override fun findAll(): List<CityResponse> {
        return jpaCityRepository.findAll().map(::mapToCityResponse)
    }

    override fun findById(id: Long): CityResponse? {
        return jpaCityRepository.findById(id)
            .map(::mapToCityResponse)
            .orElseThrow { NotFoundException("Город с ID `$id` не найден") }
    }

//    override fun findByName(name: String): CitiesListResponse {
//        val citiesList = esCityRepository.findByName(name)
//        return mapToCitiesListResponse(citiesList)
//    }

    override fun save(request: CreateCityRequest): CityResponse {
        val city = mapToCity(request)
        return mapToCityResponse(jpaCityRepository.save(city))
    }

    @Transactional
    override fun update(id: Long, request: CreateCityRequest): CityResponse {
        jpaCityRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Город с ID `$id` не найден") }
        val editedCity = updateTicket(id, request)
        return mapToCityResponse(jpaCityRepository.save(editedCity))
    }

    override fun delete(id: Long) {
        jpaCityRepository.deleteById(id)
    }

    private fun mapToCityResponse(jpaCity: JpaCity) = CityResponse(id = jpaCity.id!!, name = jpaCity.name)

//    private fun mapToCitiesListResponse(cities: List<EsCity>) =
//        CitiesListResponse(cities.map(::mapToCityObject))
//
//    private fun mapToCityObject(esCity: EsCity) = CityObject(id = esCity.id!!, name = esCity.name)

    private fun mapToCity(request: CreateCityRequest) = JpaCity(name = request.name)

    private fun updateTicket(citytId: Long, request: CreateCityRequest) = JpaCity(id = citytId, name = request.name)

}
