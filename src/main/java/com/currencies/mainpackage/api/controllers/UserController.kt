package com.currencies.mainpackage.api.controllers

import com.currencies.mainpackage.api.ApiPath.HOME
import com.currencies.mainpackage.api.ApiPath.ID
import com.currencies.mainpackage.api.ApiPath.LIST
import com.currencies.mainpackage.api.ApiPath.PASSWORD
import com.currencies.mainpackage.api.ApiPath.PROFILE
import com.currencies.mainpackage.api.ApiPath.SIGN_UP
import com.currencies.mainpackage.api.ApiPath.USERS
import com.currencies.mainpackage.api.docs.SwaggerUserController
import com.currencies.mainpackage.api.dto.request.ChangePasswordRequest
import com.currencies.mainpackage.api.dto.request.ChangeUserDataRequest
import com.currencies.mainpackage.api.dto.request.UserRegistrationRequest
import com.currencies.mainpackage.api.dto.response.UserResponse
import com.currencies.mainpackage.core.user.UserService
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping(USERS)
class UserController(private val userService: UserService) : SwaggerUserController {

    @PostMapping(SIGN_UP)
    override fun signUp(
        @ModelAttribute @Valid userRegistrationRequest: UserRegistrationRequest,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                "userRegistrationRequest",
                userRegistrationRequest
            )
            redirectAttributes.addFlashAttribute(
                "org.springframework.validation.BindingResult.userRegistrationRequest",
                bindingResult
            )
            return "redirect:$SIGN_UP"
        }
        userService.create(userRegistrationRequest)
        return "redirect:$HOME"
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(ID)
    override fun getById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        val userResponse: UserResponse = userService.getById(id)
        return ResponseEntity.ok().body(userResponse)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(LIST)
    override fun getUsers(
        @RequestParam @PositiveOrZero(message = "Номер страницы не может быть меньше нуля.") page: Int,
        @RequestParam @Positive(message = "Количество элементов на странице не может быть меньше одного.") limit: Int
    ): ResponseEntity<List<UserResponse>> {
        val users: List<UserResponse> = userService.getUsers(page, limit)
        return ResponseEntity.ok().body(users)
    }

    @GetMapping(PROFILE)
    override fun getProfile(): ResponseEntity<UserResponse> {
        val userResponse: UserResponse = userService.authenticatedUser
        return ResponseEntity.ok().body(userResponse)
    }

    @PutMapping(PROFILE + PASSWORD)
    override fun editPassword(@RequestBody @Valid changePasswordRequest: ChangePasswordRequest): ResponseEntity<UserResponse> {
        val userResponse: UserResponse = userService.editPassword(
            userService.authenticatedUser.id,
            changePasswordRequest
        )
        return ResponseEntity.ok().body(userResponse)
    }

    @PatchMapping(PROFILE)
    override fun editProfileData(@RequestBody @Valid changeUserDataRequest: ChangeUserDataRequest): ResponseEntity<UserResponse> {
        val userResponse: UserResponse = userService.editData(
            userService.authenticatedUser.id,
            changeUserDataRequest
        )
        return ResponseEntity.ok().body(userResponse)
    }
}
