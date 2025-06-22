package com.currencies.mainpackage.core.security

import com.currencies.mainpackage.api.dto.request.LoginRequest
import com.currencies.mainpackage.api.dto.response.UsernameResponse

interface AuthService {

    fun authenticate(loginRequest: LoginRequest): UsernameResponse

}
