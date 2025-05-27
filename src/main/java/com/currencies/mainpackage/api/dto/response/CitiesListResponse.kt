package com.currencies.mainpackage.api.dto.response

import com.currencies.mainpackage.api.dto.response.`object`.CityObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED

data class CitiesListResponse(

    @Schema(
        description = "Список городов",
        requiredMode = REQUIRED,
    )
    val cities: List<CityObject>

)
