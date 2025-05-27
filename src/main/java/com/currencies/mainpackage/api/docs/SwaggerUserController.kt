package com.currencies.mainpackage.api.docs

import com.currencies.mainpackage.api.docs.apiresponse.ApiResponseIllegalValue
import com.currencies.mainpackage.api.docs.apiresponse.ApiResponseNotFound
import com.currencies.mainpackage.api.dto.request.ChangePasswordRequest
import com.currencies.mainpackage.api.dto.request.ChangeUserDataRequest
import com.currencies.mainpackage.api.dto.request.UserRegistrationRequest
import com.currencies.mainpackage.api.dto.response.UserResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

interface SwaggerUserController {

    @ApiResponseIllegalValue
    fun signUp(
        @ModelAttribute @Valid userRegistrationRequest: UserRegistrationRequest,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String

    @ApiResponseIllegalValue
    @ApiResponseNotFound
//    @ApiResponseSessionIsOver
    @SecurityRequirement(name = "JWT")
    fun getById(
        @PathVariable @Parameter(example = "1") id: Long
    ): ResponseEntity<UserResponse>

    @ApiResponseIllegalValue
//    @ApiResponseSessionIsOver
    fun getUsers(
        @RequestParam
        @PositiveOrZero(message = "Номер страницы не может быть меньше нуля.")
        @Parameter(example = "0")
        page: Int,
        @RequestParam
        @Positive(message = "Количество элементов на странице не может быть меньше одного.")
        @Parameter(example = "1")
        limit: Int
    ): ResponseEntity<List<UserResponse>>

    @Operation(summary = "Информация о текущем авторизованном пользователе")
//    @ApiResponseSessionIsOver
    @SecurityRequirement(name = "JWT")
    fun getProfile(): ResponseEntity<UserResponse>

    @ApiResponseIllegalValue
    @ApiResponseNotFound
//    @ApiResponseSessionIsOver
    @SecurityRequirement(name = "JWT")
    fun editPassword(
        @RequestBody @Valid changePasswordRequest: ChangePasswordRequest
    ): ResponseEntity<UserResponse>

    @ApiResponseIllegalValue
    @ApiResponseNotFound
//    @ApiResponseSessionIsOver
    @SecurityRequirement(name = "JWT")
    fun editProfileData(
        @RequestBody @Valid changeUserDataRequest: ChangeUserDataRequest
    ): ResponseEntity<UserResponse>

}