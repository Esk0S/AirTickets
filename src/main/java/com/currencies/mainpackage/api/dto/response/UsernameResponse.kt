package com.currencies.mainpackage.api.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED
import java.io.Serializable

data class UsernameResponse(

    @Schema(
        description = "Имя авторизованного пользователя",
        requiredMode = REQUIRED,
        example = "my_login123"
    )
    val username: String

) : Serializable
