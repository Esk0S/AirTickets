package com.currencies.mainpackage.core.user

import com.currencies.mainpackage.api.dto.request.ChangePasswordRequest
import com.currencies.mainpackage.api.dto.request.ChangeUserDataRequest
import com.currencies.mainpackage.api.dto.request.UserRegistrationRequest
import com.currencies.mainpackage.api.dto.response.UserResponse

interface UserService {

    val authenticatedUser: UserResponse

    fun create(userDto: UserRegistrationRequest): UserResponse

    fun delete(id: Long)

    fun editData(id: Long, changeUserDataRequest: ChangeUserDataRequest): UserResponse

    fun editPassword(id: Long, changePasswordRequest: ChangePasswordRequest): UserResponse

//    fun changeActiveStatus(id: Long, isEnabled: Boolean)

    fun getById(id: Long): UserResponse

//    fun addRole(userId: Long, roleId: Long): UserResponse
//
//    fun removeRole(userId: Long, roleId: Long): UserResponse

    fun getUsers(pageNumber: Int, pageSize: Int): List<UserResponse>

}
