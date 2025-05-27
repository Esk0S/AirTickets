//package com.currencies.mainpackage.entities.es
//
//import jakarta.persistence.GeneratedValue
//import jakarta.persistence.GenerationType
//import jakarta.persistence.Id
//import java.io.Serializable
//import org.springframework.data.elasticsearch.annotations.Document
//import org.springframework.data.elasticsearch.annotations.Field
//
//@Document(indexName = "cities")
//data class EsCity (
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Long? = null,
//
//    @Field("city_name")
//    val name: String
//
//) : Serializable