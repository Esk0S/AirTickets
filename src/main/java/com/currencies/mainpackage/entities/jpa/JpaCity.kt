package com.currencies.mainpackage.entities.jpa

import com.currencies.mainpackage.entities.es.EsCity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name = "city")
data class JpaCity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is EsCity) {
            return this.id == other.id && this.name == other.name
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        super.hashCode()
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        return result
    }

}