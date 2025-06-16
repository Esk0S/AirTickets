package com.currencies.mainpackage.api.dto.response.`object`

data class UserObject(

    var id: Long,

    val roles: Set<RoleObject>?,

    var username: String,

    var email: String,

    var firstName: String? = null,

    var lastName: String? = null,

    var middleName: String? = null

)
