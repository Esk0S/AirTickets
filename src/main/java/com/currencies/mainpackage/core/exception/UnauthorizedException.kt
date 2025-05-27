package com.currencies.mainpackage.core.exception

class UnauthorizedException : BaseException {

    companion object {
        const val UNAUTHORIZED_MESSAGE = "Не удалось авторизовать пользователя."
    }

    constructor() : super(UNAUTHORIZED_MESSAGE)

    constructor(message: String, vararg params: Any) : super(message, params.asList())

    constructor(cause: Throwable) : super(UNAUTHORIZED_MESSAGE, cause)

    constructor(message: String, cause: Throwable, vararg params: Any) : super(message, cause, params.asList())

}