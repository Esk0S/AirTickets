package com.currencies.mainpackage.api.dto.request

import com.currencies.mainpackage.core.PATTERN_EMAIL
import com.currencies.mainpackage.core.PATTERN_LOGIN
import com.currencies.mainpackage.core.PATTERN_PASSWORD
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.io.Serializable

data class UserRegistrationRequest (

    @Schema(
        description = "Имя пользователя",
        requiredMode = REQUIRED,
        example = "my_login123"
    )
    @field:Size(min = 3, max = 20, message = "Имя пользователя должно быть в диапазоне от {min} до {max}.")
    @field:Pattern(regexp = PATTERN_LOGIN, message = "Имя пользователя должно содержать цифры, латинские буквы или знак _")
    val username: String,

    @Schema(
        description = "Пароль пользователя",
        requiredMode = REQUIRED,
        example = "Password123@"
    )
    @field:Pattern(
        regexp = PATTERN_PASSWORD,
        message =
            "Пароль:" +
            "<ul>" +
            "<li>Длина пароля должна быть в диапазоне от 8 до 24</li>" +
            "<li>Цифры от 0 до 9</li>" +
            "<li>Строчные и заглавные латинские буквы</li>" +
            "<li>Хотя бы один из следующих символов: @, #, $, %, ^, &, +, =</li>" +
            "</ul>"
    )
    val password: String,

    @Schema(
        description = "Адрес электронной почты",
        requiredMode = REQUIRED,
        example = "my.email@mail.ru"
    )
    @field:Size(max = 100, message = "Длина адреса почты должна быть в диапазоне от {min} до {max}.")
    @field:Pattern(regexp = PATTERN_EMAIL, message = "Неверный формат эл. почты.")
    val email: String,

    @Schema(
        description = "Имя",
        requiredMode = NOT_REQUIRED,
        example = "Nikolay"
    )
    @field:Size(max = 30, message = "Длина имени должна быть в диапазоне до {max}.")
    val firstName: String? = null,

    @Schema(
        description = "Фамилия",
        requiredMode = NOT_REQUIRED,
        example = "Petrov"
    )
    @field:Size(max = 30, message = "Длина фамилии должна быть в диапазоне до {max}.")
    val lastName: String? = null,

    @Schema(
        description = "Отчество",
        requiredMode = NOT_REQUIRED,
        example = "Alexandrovich"
    )
    @field:Size(max = 30, message = "Длина отчества должна быть в диапазоне до {max}.")
    val middleName: String? = null,

) : Serializable
