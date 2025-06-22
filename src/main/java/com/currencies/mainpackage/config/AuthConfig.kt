package com.currencies.mainpackage.config

import com.currencies.mainpackage.api.ApiPath.LOGIN
import com.currencies.mainpackage.api.ApiPath.LOGOUT
import com.currencies.mainpackage.api.ApiPath.SIGN_UP
import com.currencies.mainpackage.api.ApiPath.USERS
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices.TWO_WEEKS_S
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession

@Configuration
@EnableWebSecurity
@EnableJdbcHttpSession
class AuthConfig(
    @Value("\${app.key}") private val secretKey: String
) {

    companion object {
        private const val ADMIN = "ADMIN"
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authResourcesFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.IF_REQUIRED
            }
            authorizeHttpRequests {
                authorize("/swagger.html", hasRole(ADMIN))
                authorize("/swagger-ui/*", hasRole(ADMIN))
                authorize("/api-docs/**", hasRole(ADMIN))

                authorize(HttpMethod.GET, "/css/**", permitAll)
                authorize(HttpMethod.GET, "/js/**", permitAll)
                authorize(HttpMethod.GET, "/images/**", permitAll)

                authorize(HttpMethod.GET, SIGN_UP, permitAll)
                authorize(HttpMethod.POST, USERS + SIGN_UP, permitAll)
                authorize(anyRequest, authenticated)
            }
            rememberMe {
                key = secretKey
                rememberMeParameter = "remember-me"
                tokenValiditySeconds = TWO_WEEKS_S
                useSecureCookie = true
                alwaysRemember = true
            }
            formLogin {
                loginPage = LOGIN
                loginProcessingUrl = LOGIN
                permitAll = true
            }
            logout {
                logoutUrl = LOGOUT
                logoutSuccessUrl = LOGIN
                permitAll = true
            }
        }
        return http.build()
    }

}