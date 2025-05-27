package com.currencies.mainpackage.core

import com.currencies.mainpackage.core.exception.DbUniquenessViolationException

const val PATTERN_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"

const val PATTERN_LOGIN = "^[0-9a-zA-Z_]+$"

const val PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.])(?=\\S+$).*$"

fun checkFieldUniqueness(isFound: Boolean, errorMessage: String) {
    if (isFound) {
        throw DbUniquenessViolationException(errorMessage)
    }
}