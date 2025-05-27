package com.currencies.mainpackage.api.dto.request

import com.currencies.mainpackage.core.PATTERN_PASSWORD
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.io.Serializable

data class ChangePasswordRequest (

    @Schema(
        description = "Новый пароль пользователя",
        requiredMode = REQUIRED,
        example = "Password123@"
    )
    @Size(min = 8, max = 24, message = "Длина пароля должна быть в диапазоне от {min} до {max}.")
    @Pattern(
        regexp = PATTERN_PASSWORD,
        message = "Пароль должен содержать:\n" +
            "Цифры от 0 до 9\n" +
            "Строчные и заглавные латинские буквы\n" +
            "Хотя бы один из следующих символов: @, #, $, %, ^, &, +, ="
    )
    val newPassword: String

) : Serializable