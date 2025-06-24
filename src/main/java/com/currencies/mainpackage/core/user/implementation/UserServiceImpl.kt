package com.currencies.mainpackage.core.user.implementation

import com.currencies.mainpackage.api.dto.request.ChangePasswordRequest
import com.currencies.mainpackage.api.dto.request.ChangeUserDataRequest
import com.currencies.mainpackage.api.dto.request.LoginRequest
import com.currencies.mainpackage.api.dto.request.UserRegistrationRequest
import com.currencies.mainpackage.api.dto.response.UserResponse
import com.currencies.mainpackage.core.checkFieldUniqueness
import com.currencies.mainpackage.core.exception.NotFoundException
import com.currencies.mainpackage.core.exception.UnauthorizedException
import com.currencies.mainpackage.core.security.AuthService
import com.currencies.mainpackage.core.security.UserPrincipal
import com.currencies.mainpackage.core.user.UserService
import com.currencies.mainpackage.entities.Role
import com.currencies.mainpackage.entities.User
import com.currencies.mainpackage.repositories.RoleRepository
import com.currencies.mainpackage.repositories.UserRepository
import org.apache.logging.log4j.Marker
import org.apache.logging.log4j.MarkerManager
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Service
class UserServiceImpl (
    private val encoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val authService: AuthService
) : UserService, Logging {

    companion object {
        private val marker: Marker = MarkerManager.getMarker("USER SERVICE")

        private const val ROLE_USER = "ROLE_USER"
    }

    override val authenticatedUser: UserResponse
        get() = currentUser.toUserResponse()

    private val currentUser: User
        get() {
            val authentication: Authentication =
                SecurityContextHolder.getContext().authentication ?: throw UnauthorizedException()
            return (authentication.principal as UserPrincipal).user
        }

    override fun create(userDto: UserRegistrationRequest): UserResponse {
        checkFieldUniqueness(
            userRepository.findByEmail(userDto.email) != null,
            "Пользователь с email `${userDto.email}` уже существует."
        )
        checkFieldUniqueness(
            userRepository.findByUsername(userDto.username) != null,
            "Пользователь с именем `${userDto.username}` уже существует."
        )

        val user = userDto.toUser()
        userRepository.save(user)
        logger.info(marker, "User ${user.username} created")

        authService.authenticate(LoginRequest(userDto.username, userDto.password))

        return user.toUserResponse()
    }

    @Transactional
    override fun delete(id: Long) {
        val user: User = findUserByIdOrThrowException(id)

        userRepository.delete(user)

        logger.info(marker, "User ${user.username} deleted")
    }

    @Transactional
    override fun editData(id: Long, changeUserDataRequest: ChangeUserDataRequest): UserResponse {
        val user = findUserByIdOrThrowException(id)

        val userCopy = user.copy(
            firstName = changeUserDataRequest.firstName ?: user.firstName,
            lastName = changeUserDataRequest.lastName ?: user.lastName,
            middleName = changeUserDataRequest.middleName ?: user.middleName
        )

        val updatedUser = userRepository.save(userCopy)

        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val session = request.getSession(false)
        session?.invalidate()

        logger.info(marker, "User ${user.username} has changed their data")
        return updatedUser.toUserResponse()
    }

    @Transactional
    override fun editPassword(id: Long, changePasswordRequest: ChangePasswordRequest): UserResponse {
        val user: User = findUserByIdOrThrowException(id)
        val updatedUser = user.copy(password = encoder.encode(changePasswordRequest.newPassword))
        userRepository.save(updatedUser)

        logger.info(marker, "User ${user.username} has changed their password")
        return user.toUserResponse()
    }

    override fun getById(id: Long): UserResponse {
        val user: User = findUserByIdOrThrowException(id)

        logger.info(marker, "Received user: ${user.username}")
        return user.toUserResponse()
    }

    override fun getUsers(pageNumber: Int, pageSize: Int): List<UserResponse> {
        val pageable: Pageable = PageRequest.of(pageNumber, pageSize)
        val users = userRepository.findAll(pageable).map{ it.toUserResponse() }.toList()

        logger.info(marker, "Number of received users: ${users.size}")
        return users
    }

//    @Transactional
//    override fun changeActiveStatus(id: Long, isEnabled: Boolean) {
//        val user: User = findUserByIdOrThrowException(id)
//        user.setEnabled(isEnabled)
//        userRepository.save(user)
//
//        logger.info(marker, "User ${id} has changed `isEnabled` status to ${isEnabled}.")
//    }
//
//    @Transactional
//    override fun addRole(userId: Long, roleId: Long): UserResponse {
//        val user: User = findUserByIdOrThrowException(userId)
//        val roles: MutableSet<Role> = HashSet<Any?>(user.roles)
//        val newRole: Role = findRoleByIdOrThrowException(roleId)
//
//        if (roles.contains(newRole)) {
//            return Mapper.map(user)
//        }
//
//        roles.add(newRole)
//        user.setRoles(roles)
//        userRepository.save(user)
//
//        logger.info(marker, "Added role ${newRole.name} to user ${user.username}")
//        return Mapper.map(user)
//    }
//
//    @Transactional
//    override fun removeRole(userId: Long, roleId: Long): UserResponse {
//        val user: User = findUserByIdOrThrowException(userId)
//        val roles: MutableSet<Role> = HashSet<Any?>(user.getRoles())
//        val roleToRemove: Role = findRoleByIdOrThrowException(roleId)
//
//        if (!roles.contains(roleToRemove)) {
//            return Mapper.map(user)
//        }
//
//        roles.remove(roleToRemove)
//        user.setRoles(roles)
//        userRepository.save(user)
//
//        logger.info(marker, "Removed role ${roleToRemove.name} from user ${user.username}")
//        return Mapper.map(user)
//    }
//
//    private fun initRoles(roleName: User): User {
//        val newRoles = findRoleByNameOrThrowException(roleName)
//        return user.copy(roles = newRoles)
//    }

    private fun findUserByIdOrThrowException(userId: Long?): User {
        userId?.let {
            return userRepository.findByIdOrNull(userId)
                ?: throw NotFoundException("Пользователь с id `$userId` не найден.")
        } ?: throw NotFoundException("Отсутствует id пользователя для поиска")
    }

//    private fun findRoleByIdOrThrowException(roleId: Long?): Role {
//        roleId?.let {
//            return roleRepository.findByIdOrNull(it)
//                ?: throw NotFoundException("Роль с id `{0}` не найдена.", it)
//        } ?: throw NotFoundException("Отсутствует id роли для поиска")
//    }

    private fun findRoleByNameOrThrowException(name: String): Role {
        return roleRepository.findByName(name)
            ?: throw NotFoundException("Роль `$name` не найдена.")
    }

    private fun UserRegistrationRequest.toUser(): User {
        return User(
            email = this.email,
            username = this.username,
            password = encoder.encode(this.password),
            roles = setOf(findRoleByNameOrThrowException(ROLE_USER)),
            firstName = this.firstName,
            lastName = this.lastName,
            middleName = this.middleName,
            isEnabled = true
        )
    }

    private fun User.toUserResponse(): UserResponse {
        return UserResponse(
            id = this.id!!,
            roles = this.roles.map { it.id!! }.toSet(),
            username = this.username,
            email = this.email,
            firstName = this.firstName,
            lastName = this.lastName,
            middleName = this.middleName
        )
    }

}
