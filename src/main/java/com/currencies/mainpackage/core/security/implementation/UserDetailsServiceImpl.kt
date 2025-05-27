package com.currencies.mainpackage.core.security.implementation

import com.currencies.mainpackage.core.security.UserPrincipal
import com.currencies.mainpackage.repositories.UserRepository
import org.apache.logging.log4j.Marker
import org.apache.logging.log4j.MarkerManager
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService, Logging {

    private val marker: Marker = MarkerManager.getMarker("USER SERVICE")

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        logger.info(marker, "User $username found")
        return UserPrincipal(user)
//        return User(
//            user.username,
//            user.password,
//            user.isEnabled,
//            true,
//            true,
//            true,
//            user.roles.map { SimpleGrantedAuthority(it.name) }
//        )
    }

}