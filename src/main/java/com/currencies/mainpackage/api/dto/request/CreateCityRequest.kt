package com.currencies.mainpackage.api.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED

data class CreateCityRequest(

    @Schema(
        description = "Название города",
        requiredMode = REQUIRED,
        example = "Новосибирск"
    )
    val name: String,

)
