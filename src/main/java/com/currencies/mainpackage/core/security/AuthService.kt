package com.currencies.mainpackage.core.security

import com.currencies.mainpackage.api.dto.request.LoginRequest
import com.currencies.mainpackage.api.dto.response.UsernameResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

interface AuthService {
    fun authenticate(loginRequest: LoginRequest): UsernameResponse

//    fun setAuthentication(request: HttpServletRequest, response: HttpServletResponse): Boolean

//    fun signOut()

//    fun checkUserByToken(checkTokenRequest: CheckTokenRequest): CheckTokenUserInfoResponse
}
