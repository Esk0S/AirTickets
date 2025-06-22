package com.currencies.mainpackage.api.controllers

import com.currencies.mainpackage.api.ApiPath.ALL
import com.currencies.mainpackage.api.ApiPath.CITIES
import com.currencies.mainpackage.api.ApiPath.ES
import com.currencies.mainpackage.api.ApiPath.ID
import com.currencies.mainpackage.api.ApiPath.NAME
import com.currencies.mainpackage.api.ApiPath.REINDEX
import com.currencies.mainpackage.api.dto.request.CreateCityRequest
import com.currencies.mainpackage.api.dto.response.CityResponse
import com.currencies.mainpackage.core.city.CityService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@CrossOrigin
@RequestMapping(CITIES)
class CityController(private val cityService: CityService) {

    @GetMapping(REINDEX)
    fun reindexAll(): ResponseEntity<Void> {
        cityService.reindexAll()
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<CityResponse>> {
        val cities = cityService.findAll()
        return ResponseEntity.ok().body(cities)
    }

    @GetMapping(ID)
    fun findById(@PathVariable id: Long): ResponseEntity<CityResponse> {
        return ResponseEntity.ok().body(cityService.findById(id))
    }

    @GetMapping(NAME)
    fun findByName(
        @RequestParam query: String,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int,
        model: Model
    ): String {
        val cities = cityService.findByName(query, page, size).map { it.name }.toList()
        model.addAttribute("cities", cities)
        return "fragments/citySuggestions :: cityList"
    }

    @PostMapping(NAME)
    fun create(@RequestBody request: CreateCityRequest): ResponseEntity<CityResponse> {
        return ResponseEntity.ok().body(cityService.save(request))
    }

    @PostMapping
    fun createJpa(@RequestBody request: CreateCityRequest): ResponseEntity<CityResponse> {
        return ResponseEntity.ok().body(cityService.saveJpa(request))
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

    @DeleteMapping(ES + ID)
    fun deleteEs(@PathVariable id: Long): ResponseEntity<Void> {
        cityService.deleteEs(id)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping(ES + ALL)
    fun deleteAllEs(): ResponseEntity<Void> {
        cityService.deleteAllEs()
        return ResponseEntity.noContent().build()
    }

}
