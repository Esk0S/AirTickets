package com.currencies.mainpackage.core.exception

class StorageConsistencyException : BaseException {

    companion object {
        private const val DEFAULT_MESSAGE = "Сущности из ElasticSearch и базы данных не одинаковы."
    }

    constructor() : super(DEFAULT_MESSAGE)

    constructor(message: String, vararg params: Any) : super(message, params.asList())

    constructor(cause: Throwable) : super(DEFAULT_MESSAGE, cause)

    constructor(message: String, cause: Throwable, vararg params: Any) : super(message, cause, params.asList())

}