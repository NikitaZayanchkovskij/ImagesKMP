package com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling

/** Similar to a Result class from the standard Kotlin library,
 * but with extended functionality.
 *
 * For example: in case of an error - Result class from Kotlin only allows to
 * pass an exception (throwable), without a body.
 *
 * In this class we can pass a body if needed, as a generic of type Error.
 * For example: { "error": "Your password has no special character" }
 * In this case, the generic error is for example this class:
 * PasswordErrorDto(val error: String)
 *
 * Word "out" is needed to be able to pass subtypes,
 * of what we pass as Data, or as Error.
 * (For example: DataError.Remote.UNAUTHORIZED)
 *
 * And also to be able to receive ONLY Success, or ONLY Failure.
 */
sealed interface CustomResult<out Data, out Error : CustomError> {
    data class Success<out Data>(val data: Data) : CustomResult<Data, Nothing>
    data class Failure<out Error : CustomError>(val error: Error) : CustomResult<Nothing, Error>
}

inline fun <Type, Error : CustomError, Result> CustomResult<Type, Error>.map(
    map: (Type) -> Result
): CustomResult<Result, Error> {
    return when (this) {
        is CustomResult.Success -> CustomResult.Success(data = map(data))
        is CustomResult.Failure -> CustomResult.Failure(error = error)
    }
}

inline fun <Type, Error : CustomError> CustomResult<Type, Error>.onSuccess(
    action: (Type) -> Unit
): CustomResult<Type, Error> {
    return when (this) {
        is CustomResult.Success -> {
            action(data)
            this
        }

        is CustomResult.Failure -> this
    }
}

inline fun <Type, Error : CustomError> CustomResult<Type, Error>.onFailure(
    action: (Error) -> Unit
): CustomResult<Type, Error> {
    return when (this) {
        is CustomResult.Success -> this
        is CustomResult.Failure -> {
            action(error)
            this
        }
    }
}

fun <Type, Error : CustomError> CustomResult<Type, Error>.asEmptyResult(): EmptyResult<Error> {
    return map { }
}

typealias EmptyResult<Error> = CustomResult<Unit, Error>