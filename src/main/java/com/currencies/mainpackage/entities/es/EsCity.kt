package com.currencies.mainpackage.entities.es

import com.currencies.mainpackage.entities.jpa.JpaCity
import jakarta.persistence.Id
import java.io.Serializable
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field

@Document(indexName = "cities")
data class EsCity (

    @Id
    val id: Long? = null,

    @Field("city_name")
    val name: String,

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is JpaCity) {
            return this.id == other.id && this.name == other.name
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        return result
    }

}