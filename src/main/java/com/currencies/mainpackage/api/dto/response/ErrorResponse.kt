package com.currencies.mainpackage.api.dto.response

import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED
import io.swagger.v3.oas.annotations.media.Schema

data class ErrorResponse(

    @Schema(
        description = "HTTP код ошибки",
        requiredMode = REQUIRED,
        example = "404"
    )
    val status: Int,

    @Schema(
        description = "Расшифровка HTTP кода",
        requiredMode = REQUIRED,
        example = "Not Found"
    )
    val error: String,

    @Schema(
        description = "Описание ошибки",
        requiredMode = REQUIRED,
        example = "ID not found"
    )
    val message: String? = null

)
