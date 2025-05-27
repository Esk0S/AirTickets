//package com.currencies.mainpackage.api.filter
//
//import com.currencies.mainpackage.core.security.AuthService
//import jakarta.servlet.FilterChain
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.servlet.http.HttpServletResponse
//import org.springframework.web.filter.OncePerRequestFilter
//
//class AuthTokenFilter(private val authService: AuthService) : OncePerRequestFilter() {
//
//    override fun doFilterInternal(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        filterChain: FilterChain
//    ) {
//        if (authService.setAuthentication(request, response)) {
//            filterChain.doFilter(request, response)
//        }
//    }
//
//}
