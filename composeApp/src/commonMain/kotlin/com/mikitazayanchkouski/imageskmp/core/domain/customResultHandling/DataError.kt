package com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling

sealed interface DataError : CustomError {

    enum class Remote : DataError {
        BAD_REQUEST,
        REQUEST_TIMEOUT,
        REDIRECT,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERVER_UNAVAILABLE,
        JSON_CONVERSION,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local : DataError {
        STORAGE_IS_FULL,
        FILE_NOT_FOUND,
        UNKNOWN
    }
}