package com.currencies.mainpackage.core.city.implementation

import com.currencies.mainpackage.api.dto.request.CreateCityRequest
import com.currencies.mainpackage.api.dto.response.CitiesListResponse
import com.currencies.mainpackage.api.dto.response.CityResponse
import com.currencies.mainpackage.api.dto.response.`object`.CityObject
import com.currencies.mainpackage.core.checkFieldUniqueness
import com.currencies.mainpackage.core.city.CityService
import com.currencies.mainpackage.core.exception.NotFoundException
import com.currencies.mainpackage.core.exception.StorageConsistencyException
import com.currencies.mainpackage.entities.es.EsCity
import com.currencies.mainpackage.entities.jpa.JpaCity
import com.currencies.mainpackage.repositories.es.EsCityRepository
import com.currencies.mainpackage.repositories.jpa.JpaCityRepository
import jakarta.persistence.EntityNotFoundException
import java.util.Comparator.comparingInt
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CityServiceImpl(
    private val jpaCityRepository: JpaCityRepository,
    private val esCityRepository: EsCityRepository
) : CityService, Logging {

    override fun reindexAll() {
        val cities = jpaCityRepository.findAll()

        esCityRepository.deleteAll()
        esCityRepository.saveAll(cities.map(::mapToEsCity))
    }

    override fun findAll(): List<CityResponse> {
        return jpaCityRepository.findAll().map(::mapToCityResponse)
    }

    override fun findById(id: Long): CityResponse? {
        return jpaCityRepository.findById(id)
            .map(::mapToCityResponse)
            .orElseThrow { NotFoundException("Город с ID `$id` не найден") }
    }

    override fun findByName(name: String, page: Int, size: Int): List<CityResponse> {
        val pageable = PageRequest.of(page, size)
        val searchResult = esCityRepository.searchForCities(name, pageable)
        val esCities = searchResult.getContent()

        return esCities.map(::mapToCityResponse)
    }

    override fun saveJpa(request: CreateCityRequest): CityResponse {
        val city = mapToJpaCity(request)
        return mapToCityResponse(jpaCityRepository.save(city))
    }

    @Transactional
    override fun save(request: CreateCityRequest): CityResponse {
        checkFieldUniqueness(
            esCityRepository.findByName(
                request.name
            ) != null,
            "Город `${request.name}` уже добавлен в индексацию"
        )
        checkFieldUniqueness(
            jpaCityRepository.findByName(
                request.name
            ) != null,
            "Город `${request.name}` уже добавлен в бд"
        )

        return saveCity(mapToJpaCity(request))
    }

    @Transactional
    override fun update(id: Long, request: CreateCityRequest): CityResponse {
        jpaCityRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Город с ID `$id` не найден в бд") }
        jpaCityRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Город с ID `$id` не найден в индексах") }

        val updatedCityJpa = updateTicketJpa(id, request)

        return saveCity(updatedCityJpa)
    }

    override fun delete(id: Long) {
        jpaCityRepository.deleteById(id)
    }

    override fun deleteEs(id: Long) {
        esCityRepository.deleteById(id)
    }

    override fun deleteAllEs() {
        esCityRepository.deleteAll()
    }

    private fun mapToEsCity(jpaCity: JpaCity) = EsCity(id = jpaCity.id, name = jpaCity.name)

    private fun mapToCityResponse(jpaCity: JpaCity) = CityResponse(id = jpaCity.id!!, name = jpaCity.name)

    private fun mapToCityResponse(esCity: EsCity) = CityResponse(id = esCity.id!!, name = esCity.name)

    private fun mapToCitiesListResponse(cities: List<EsCity>) =
        CitiesListResponse(cities.map(::mapToCityObject))

    private fun mapToCityObject(esCity: EsCity) = CityObject(id = esCity.id!!, name = esCity.name)

    private fun mapToJpaCity(request: CreateCityRequest) = JpaCity(name = request.name)

    private fun mapToJpaCity(esCity: EsCity) = JpaCity(id = esCity.id, name = esCity.name)

    private fun mapToEsCity(request: CreateCityRequest) = EsCity(name = request.name)

    private fun updateTicketJpa(cityId: Long, request: CreateCityRequest) = JpaCity(id = cityId, name = request.name)

    private fun saveCity(jpaCity: JpaCity): CityResponse {
        val jpaCity = jpaCityRepository.save(jpaCity)
        try {
            val esCity = esCityRepository.save(mapToEsCity(jpaCity))
            if (jpaCity != esCity) {
                throw StorageConsistencyException()
            }
        } catch (e: Exception) {
            jpaCityRepository.delete(jpaCity)
            throw e
        }

        return mapToCityResponse(jpaCity)
    }

}
