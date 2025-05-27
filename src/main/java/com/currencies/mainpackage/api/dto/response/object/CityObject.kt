package com.currencies.mainpackage.api.dto.response.`object`

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED

data class CityObject(

    @Schema(
        description = "ID города",
        requiredMode = REQUIRED,
        example = "1"
    )
    val id: Long,

    @Schema(
        description = "Название города",
        requiredMode = REQUIRED,
        example = "Новосибирск"
    )
    val name: String,

)
