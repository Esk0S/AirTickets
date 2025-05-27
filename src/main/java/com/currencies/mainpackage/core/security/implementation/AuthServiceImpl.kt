package com.currencies.mainpackage.core.security.implementation

import com.currencies.mainpackage.api.dto.request.LoginRequest
import com.currencies.mainpackage.api.dto.response.UsernameResponse
import com.currencies.mainpackage.core.exception.UnauthorizedException
import com.currencies.mainpackage.core.security.AuthService
import com.currencies.mainpackage.core.security.UserPrincipal
import org.apache.logging.log4j.Marker
import org.apache.logging.log4j.MarkerManager
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.HandlerExceptionResolver

@Service
class AuthServiceImpl(
    private val userDetailsService: UserDetailsService,
    private val handlerExceptionResolver: HandlerExceptionResolver,
//    private val sessionService: SessionService
    private val authenticationManager: AuthenticationManager
//        private val tokenService: TokenService? = null
) : AuthService, Logging {

    companion object {
        private val marker: Marker = MarkerManager.getMarker("AUTH SERVICE")
    }

    override fun authenticate(loginRequest: LoginRequest): UsernameResponse {
        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            )
        } catch (e: AuthenticationException) {
            throw UnauthorizedException(e)
        }

        val userPrincipal = authentication.principal as? UserPrincipal ?: throw UnauthorizedException()

        logger.info(marker, "User ${userPrincipal.username} has been authenticated")
        return UsernameResponse(userPrincipal.username)
    }

//    override fun setAuthentication(request: HttpServletRequest, response: HttpServletResponse): Boolean {
//        val securityContext: SecurityContext = SecurityContextHolder.getContext()
//
//        if (securityContext.authentication !is JwtAuthenticationToken) {
//            logger.info("Not authenticated request: ${request.method}: ${request.requestURL}")
//            return true
//        }
//
//        try {
//            val jwt = securityContext.authentication.principal as Jwt
//
////            if (sessionService.checkSessionExpired(tokenService.getSessionId(jwt))) {
////                throw SessionIsOverException()
////            }
//
//            val userDetails = userDetailsService.loadUserByUsername(jwt.subject)
//            val authentication = UsernamePasswordAuthenticationToken(
//                userDetails,
//                null,
//                userDetails.authorities
//            )
//            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
//            securityContext.authentication = authentication
//
//            logger.info(marker, "Auth set for user: ${userDetails.username}")
//            return true
//        } catch (e: BaseException) {
//            securityContext.authentication = null
//            SecurityContextHolder.clearContext()
//            response.reset()
//            val baseException = if (e is SessionIsOverException)
//                e
//            else
//                UnauthorizedException(e)
//            handlerExceptionResolver.resolveException(
//                request,
//                response,
//                null,
//                baseException
//            )
//            return false
//        }
//    }

//    override fun signOut() {
//        val authentication = SecurityContextHolder.getContext().authentication
//        val user = (authentication.principal as UserPrincipal).user
//        sessionService.expireUserSession(user)
//        SecurityContextHolder.clearContext()
//    }

//    override fun checkUserByToken(checkTokenRequest: CheckTokenRequest): CheckTokenUserInfoResponse {
//        try {
//            val jwt: Jwt = tokenService.parseJwt(checkTokenRequest.getToken())
//            if (sessionService.checkSessionExpired(tokenService.getSessionId(jwt))) {
//                throw ServiceException(SESSION_IS_OVER)
//            }
//            val userPrincipal: UserPrincipal = userDetailsService.loadUserByUsername(jwt.getSubject()) as UserPrincipal
//            return Mapper.mapToCheckTokenResponseUserInfo(userPrincipal.getUser())
//        } catch (e: ServiceException) {
//            throw PrivateApiException("Check user by token exception", e)
//        }
//    }

}
