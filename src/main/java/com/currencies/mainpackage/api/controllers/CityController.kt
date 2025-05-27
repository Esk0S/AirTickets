package com.currencies.mainpackage.api.controllers

import com.currencies.mainpackage.api.ApiPath.CITIES
import com.currencies.mainpackage.api.ApiPath.ID
import com.currencies.mainpackage.api.ApiPath.NAME
import com.currencies.mainpackage.api.ApiPath.SEARCH
import com.currencies.mainpackage.api.dto.request.CreateCityRequest
import com.currencies.mainpackage.api.dto.request.CreateTicketRequest
import com.currencies.mainpackage.api.dto.response.CitiesListResponse
import com.currencies.mainpackage.api.dto.response.CityResponse
import com.currencies.mainpackage.core.city.CityService
import java.sql.Date
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping(CITIES)
class CityController(private val cityService: CityService) {

    @GetMapping
    fun findAll(): ResponseEntity<List<CityResponse>> {
        val cities = cityService.findAll()
        return ResponseEntity.ok().body(cities)
    }

    @GetMapping(ID)
    fun findById(@PathVariable id: Long): ResponseEntity<CityResponse> {
        return ResponseEntity.ok().body(cityService.findById(id))
    }

//    @GetMapping(NAME)
//    fun findByName(
//        @RequestParam query: String
//    ): ResponseEntity<CitiesListResponse> {
//        return ResponseEntity.ok().body(cityService.findByName(query))
//    }

//    @GetMapping(SEARCH)
//    fun findByFromAndFromTo(
//        @RequestParam fromPlace: String,
//        @RequestParam toPlace: String,
//        @RequestParam `when`: Date
//    ): ResponseEntity<List<CityResponse>> {
//        return ResponseEntity.ok().body(
//            cityService.findTickets(fromPlace, toPlace, `when`)
//        )
//    }

    @PostMapping
    fun create(@RequestBody request: CreateCityRequest): ResponseEntity<CityResponse> {
        return ResponseEntity.ok().body(cityService.save(request))
    }

    @PatchMapping(ID)
    fun update(@PathVariable id: Long, @RequestBody request: CreateCityRequest): ResponseEntity<CityResponse> {
        return ResponseEntity.ok().body(cityService.update(id, request))
    }

    @DeleteMapping(ID)
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        cityService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
