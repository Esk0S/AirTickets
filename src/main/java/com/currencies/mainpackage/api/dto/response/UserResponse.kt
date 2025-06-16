package com.currencies.mainpackage.api.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED
import java.io.Serializable

data class UserResponse (

    @Schema(
        description = "Идентификатор пользователя",
        requiredMode = REQUIRED,
        example = "160"
    )
    var id: Long,

    @Schema(
        description = "Набор идентификаторов ролей пользователя",
        requiredMode = REQUIRED,
        example = "[1, 2]"
    )
    val roles: Set<Long>,

    @Schema(
        description = "Имя пользователя",
        requiredMode = REQUIRED,
        example = "my_login"
    )
    var username: String,

    @Schema(
        description = "Адрес электронной почты",
        requiredMode = REQUIRED,
        example = "myemail@mail.ru"
    )
    var email: String,

    @Schema(
        description = "Имя",
        requiredMode = NOT_REQUIRED,
        example = "Nikolay"
    )
    var firstName: String? = null,

    @Schema(
        description = "Фамилия",
        requiredMode = NOT_REQUIRED,
        example = "Petrov"
    )
    var lastName: String? = null,

    @Schema(
        description = "Отчество",
        requiredMode = NOT_REQUIRED,
        example = "Alexandrovich"
    )
    var middleName: String? = null

) : Serializable