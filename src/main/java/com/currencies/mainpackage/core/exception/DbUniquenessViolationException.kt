package com.currencies.mainpackage.core.exception

class DbUniquenessViolationException : BaseException {

    constructor(message: String, vararg params: Any) : super(message, params.asList())

    constructor(message: String, cause: Throwable, vararg params: Any) : super(message, cause, params.asList())

}