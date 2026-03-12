package com.mikitazayanchkouski.imageskmp.core.data.network

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified Type> safeCall(
    execute: suspend () -> HttpResponse
): CustomResult<Type, DataError.Remote> {
    return runCatching {
        execute()
    }.fold(
        onSuccess = { httpResponse ->
            convertResponseToResult(httpResponse = httpResponse)
        },
        onFailure = { throwable ->
            when (throwable) {
                is SocketTimeoutException,
                is ConnectTimeoutException,
                is HttpRequestTimeoutException -> {
                    CustomResult.Failure(error = DataError.Remote.REQUEST_TIMEOUT)
                }

                is RedirectResponseException -> {
                    CustomResult.Failure(error = DataError.Remote.REDIRECT)
                }

                is UnresolvedAddressException -> {
                    CustomResult.Failure(error = DataError.Remote.NO_INTERNET)
                }

                is ServerResponseException -> {
                    CustomResult.Failure(error = DataError.Remote.SERVER_ERROR)
                }

                is ClientRequestException -> {
                    CustomResult.Failure(error = DataError.Remote.BAD_REQUEST)
                }

                is JsonConvertException -> {
                    CustomResult.Failure(error = DataError.Remote.JSON_CONVERSION)
                }

                is SerializationException -> {
                    CustomResult.Failure(error = DataError.Remote.SERIALIZATION)
                }

                else -> {
                    coroutineContext.ensureActive()
                    val platformError = platformErrorToDataError(error = throwable)
                    CustomResult.Failure(error = platformError)
                }
            }
        }
    )
}

expect fun platformErrorToDataError(error: Throwable): DataError.Remote

suspend inline fun <reified Type> convertResponseToResult(
    httpResponse: HttpResponse
): CustomResult<Type, DataError.Remote> {
    return when (httpResponse.status.value) {
        in 200..299 -> {
            runCatching {
                httpResponse.body<Type>()
            }.fold(
                onSuccess = { body ->
                    CustomResult.Success(data = body)
                },
                onFailure = { throwable ->
                    when (throwable) {
                        is NoTransformationFoundException -> {
                            CustomResult.Failure(
                                error = DataError.Remote.SERIALIZATION
                            )
                        }

                        else -> {
                            coroutineContext.ensureActive()
                            CustomResult.Failure(error = DataError.Remote.UNKNOWN)
                        }
                    }
                }
            )
        }

        in 300..399 -> CustomResult.Failure(error = DataError.Remote.REDIRECT)
        400 -> CustomResult.Failure(error = DataError.Remote.BAD_REQUEST)
        401 -> CustomResult.Failure(error = DataError.Remote.UNAUTHORIZED)
        403 -> CustomResult.Failure(error = DataError.Remote.FORBIDDEN)
        404 -> CustomResult.Failure(error = DataError.Remote.NOT_FOUND)
        408 -> CustomResult.Failure(error = DataError.Remote.REQUEST_TIMEOUT)
        409 -> CustomResult.Failure(error = DataError.Remote.CONFLICT)
        413 -> CustomResult.Failure(error = DataError.Remote.PAYLOAD_TOO_LARGE)
        429 -> CustomResult.Failure(error = DataError.Remote.TOO_MANY_REQUESTS)
        500 -> CustomResult.Failure(error = DataError.Remote.SERVER_ERROR)
        503 -> CustomResult.Failure(error = DataError.Remote.SERVER_UNAVAILABLE)
        else -> CustomResult.Failure(error = DataError.Remote.UNKNOWN)
    }
}