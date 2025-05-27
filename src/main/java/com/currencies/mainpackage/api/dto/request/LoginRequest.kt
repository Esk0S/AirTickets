package com.currencies.mainpackage.api.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED
import jakarta.validation.constraints.Size
import java.io.Serializable

data class LoginRequest (

    @Schema(
        description = "Имя пользователя",
        requiredMode = REQUIRED,
        example = "my_login123"
    )
    @Size(min = 3, max = 20, message = "Длина имени пользователя должна быть в диапазоне от {min} до {max}.")
    val username: String,

    @Schema(
        description = "Пароль пользователя",
        requiredMode = REQUIRED,
        example = "Password123@"
    )
    @Size(min = 8, max = 24, message = "Длина пароля должна быть в диапазоне от {min} до {max}.")
    val password: String

) : Serializable