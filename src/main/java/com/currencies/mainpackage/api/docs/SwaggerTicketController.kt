package com.currencies.mainpackage.api.docs

import com.currencies.mainpackage.api.docs.apiresponse.ApiResponseConflict
import com.currencies.mainpackage.api.docs.apiresponse.ApiResponseCreated
import com.currencies.mainpackage.api.docs.apiresponse.ApiResponseNoContent
import com.currencies.mainpackage.api.docs.apiresponse.ApiResponseNotFound
import com.currencies.mainpackage.api.docs.apiresponse.ApiResponseOk
import com.currencies.mainpackage.api.dto.request.CreateTicketRequest
import com.currencies.mainpackage.api.dto.response.TicketResponse
import io.swagger.v3.oas.annotations.Parameter
import java.sql.Date
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

interface SwaggerTicketController {

    @ApiResponseOk
    fun findAll(): ResponseEntity<List<TicketResponse>>

    @ApiResponseNotFound
    @ApiResponseOk
    fun findById(
        @Parameter(example = "1")
        @PathVariable id: Long
    ): ResponseEntity<TicketResponse>

    fun findByFromAndFromTo(
        @Parameter(example = "отсюда")
        @RequestParam fromPlace: String,

        @Parameter(example = "сюда")
        @RequestParam toPlace: String,

        @Parameter(example = "2025-03-30")
        @RequestParam `when`: Date
    ): ResponseEntity<List<TicketResponse>>

    @ApiResponseCreated
    @ApiResponseConflict
    fun create(@RequestBody request: CreateTicketRequest): ResponseEntity<TicketResponse>

    @ApiResponseNotFound
    @ApiResponseOk
    fun update(
        @Parameter(example = "1")
        @PathVariable id: Long,
        @RequestBody request: CreateTicketRequest
    ): ResponseEntity<TicketResponse>

    @ApiResponseNotFound
    @ApiResponseNoContent
    fun delete(
        @Parameter(example = "1")
        @PathVariable id: Long
    ): ResponseEntity<Void>

}