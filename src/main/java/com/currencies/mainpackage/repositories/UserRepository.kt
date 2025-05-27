package com.currencies.mainpackage.repositories

import com.currencies.mainpackage.entities.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : BaseRepository<User, Long> {

    fun findByUsername(username: String): User?

    fun findByEmail(email: String): User?

}