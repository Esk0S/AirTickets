package com.currencies.mainpackage.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<T, ID> : JpaRepository<T, ID> {

    @Query("SELECT t FROM #{#entityName} t WHERE t.id = :id")
    fun findByIdOrNull(id: ID): T?

}