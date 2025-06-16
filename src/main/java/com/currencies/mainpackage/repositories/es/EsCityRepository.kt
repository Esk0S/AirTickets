package com.currencies.mainpackage.repositories.es

import com.currencies.mainpackage.entities.es.EsCity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

import org.springframework.stereotype.Repository

@Repository
interface EsCityRepository : ElasticsearchRepository<EsCity, Long> {

    @Query("""
        {
            "match_phrase_prefix": {
                "city_name": "?0"
            }
        }
        """)
    fun searchForCities(name: String, pageable: Pageable): Page<EsCity>

    fun findByName(name: String): EsCity?

}