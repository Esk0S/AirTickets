package com.currencies.mainpackage.api.dto.request

import com.currencies.mainpackage.core.PATTERN_EMAIL
import com.currencies.mainpackage.core.PATTERN_LOGIN
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.io.Serializable

data class ChangeUserDataRequest (

    @Schema(
        description = "Новое имя пользователя",
        requiredMode = NOT_REQUIRED,
        example = "my_new_login123"
    )
    @Size(min = 3, max = 20, message = "Длина имени пользователя должна быть в диапазоне от {min} до {max}.")
    @Pattern(regexp = PATTERN_LOGIN, message = "Имя пользователя должно содержать цифры, латинские буквы или знак _")
    val username: String? = null,

    @Schema(
        description = "Новый адрес электронной почты",
        requiredMode = NOT_REQUIRED,
        example = "my.new.email@mail.ru"
    )
    @Size(max = 100, message = "Длина адреса почты должна быть в диапазоне от {min} до {max}.")
    @Pattern(regexp = PATTERN_EMAIL, message = "Неверный формат эл. почты.")
    val email: String? = null,

    @Schema(
        description = "Новое имя",
        requiredMode = NOT_REQUIRED,
        example = "Nikolay"
    )
    @Size(max = 30, message = "Длина имени должна быть в диапазоне от {min} до {max}.")
    val firstName: String? = null,

    @Schema(
        description = "Новая фамилия",
        requiredMode = NOT_REQUIRED,
        example = "Petrov"
    )
    @Size(max = 30, message = "Длина фамилии должна быть в диапазоне от {min} до {max}.")
    val lastName: String? = null,

    @Schema(
        description = "Новое отчество",
        requiredMode = NOT_REQUIRED,
        example = "Alexandrovich"
    )
    @Size(max = 30, message = "Длина отчества должна быть в диапазоне от {min} до {max}.")
    val middleName: String? = null

) : Serializable
