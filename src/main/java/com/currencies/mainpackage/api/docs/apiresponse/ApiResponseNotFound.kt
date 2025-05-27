package com.currencies.mainpackage.api.docs.apiresponse

import com.currencies.mainpackage.api.dto.response.ErrorResponse
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.MediaType

@ApiResponse(
    responseCode = "404",
    content = [Content(
        mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = Schema(implementation = ErrorResponse::class)
    )]
)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiResponseNotFound
