package com.currencies.mainpackage.repositories

import com.currencies.mainpackage.entities.Role
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : BaseRepository<Role, Long> {

    fun findByName(name: String): Role?

}