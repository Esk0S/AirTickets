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
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import java.sql.Timestamp
import java.util.Comparator.comparingInt
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CityServiceImpl(
    private val jpaCityRepository: JpaCityRepository,
    private val esCityRepository: EsCityRepository,
    @Value("\${app.indicesUpdatePeriodicityMs:0}") private val intervalInMilliseconds: Long
) : CityService, Logging {

    companion object{
        private const val MODIFICATION_DATE = "modificationDate"
    }

    override fun reindexAll() {
        val cities = jpaCityRepository.findAll()

        esCityRepository.deleteAll()
        esCityRepository.saveAll(cities.map(::mapToEsCity))
    }

//    @Scheduled(cron = "\${app.indicesUpdatePeriodicity}")
    @Transactional
    fun sync() {
        val citySpecification: Specification<JpaCity> =
            Specification { root: Root<JpaCity>, _: CriteriaQuery<*>?, criteriaBuilder: CriteriaBuilder ->
                getModificationDatePredicate(
                    criteriaBuilder,
                    root
                )
            }
        val cityList = if (esCityRepository.count() == 0L) {
            jpaCityRepository.findAll()
        } else {
            jpaCityRepository.findAll(citySpecification)
        }
        for (city in cityList) {
            logger.info("Syncing City - ${city.name}")
        }
        esCityRepository.saveAll(cityList.map(::mapToEsCity))
    }

    override fun findAll(): List<CityResponse> {
        return jpaCityRepository.findAll().map(::mapToCityResponse)
    }

    override fun findById(id: Long): CityResponse? {
        return jpaCityRepository.findById(id)
            .map(::mapToCityResponse)
            .orElseThrow { NotFoundException("Город с ID `$id` не найден") }
    }

//    fun suggestCities(prefix: String): List<CitySuggestResponse> {
//        val query = QueryBuilders.queryString("suggest", prefix)
//
//        val searchQuery = NativeSearchQueryBuilder()
//            .withQuery(QueryBuilders.matchQuery("suggest", prefix))
//            .build()
//
//        val searchResult = elasticsearchTemplate.queryForList(City::class.java, searchQuery)
//
//        return searchResult.map { it.cityName }
//    }

    override fun findByName(name: String, page: Int, size: Int): Page<CityResponse> {
        val pageable = PageRequest.of(page, size)
        val searchResult = esCityRepository.searchForCities(name, pageable)

        val idsMap = HashMap<Long, Int>()
        val esCities: List<EsCity> = searchResult.getContent()
        for (i in esCities.indices) {
            idsMap[esCities[i].id as Long] = i
        }

        val ids = idsMap.keys

        val citiesFromDb = jpaCityRepository.findAllById(ids)
        citiesFromDb.sortWith(comparingInt{ city -> idsMap[city.id] as Int })

        return PageImpl(
            citiesFromDb.map(::mapToCityResponse),
            pageable,
            searchResult.totalElements
        )
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

    private fun getModificationDatePredicate(cb: CriteriaBuilder, root: Root<*>): Predicate {
        val currentTime = cb.currentTimestamp()
        val currentTimeMinus = cb.literal(
            Timestamp(System.currentTimeMillis() - intervalInMilliseconds)
        )
        return cb.between(
            root[MODIFICATION_DATE],
            currentTimeMinus,
            currentTime
        )
    }

}
