package com.currencies.mainpackage.core.exception

open class BaseException : Exception {

    val params: List<Any>

    constructor() {
        params = emptyList()
    }

    constructor(message: String, vararg params: Any) : super(message) {
        this.params = params.asList()
    }

    constructor(message: String, cause: Throwable, vararg params: Any) : super(message, cause) {
        this.params = params.asList()
    }

}