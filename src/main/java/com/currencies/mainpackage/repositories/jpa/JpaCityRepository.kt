package com.currencies.mainpackage.repositories.jpa

import com.currencies.mainpackage.entities.jpa.JpaCity
import com.currencies.mainpackage.repositories.BaseRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface JpaCityRepository : BaseRepository<JpaCity, Long>, JpaSpecificationExecutor<JpaCity> {

    fun findByName(name: String): JpaCity?

}