package com.currencies.mainpackage.repositories.jpa

import com.currencies.mainpackage.entities.jpa.JpaCity
import com.currencies.mainpackage.repositories.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaCityRepository : BaseRepository<JpaCity, Long> {

    fun findByName(name: String): JpaCity?

}