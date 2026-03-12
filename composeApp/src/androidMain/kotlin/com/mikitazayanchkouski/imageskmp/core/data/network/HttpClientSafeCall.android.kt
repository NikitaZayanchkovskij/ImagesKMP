package com.mikitazayanchkouski.imageskmp.core.data.network

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

actual fun platformErrorToDataError(error: Throwable): DataError.Remote {
    return when (error) {
        is UnknownHostException -> DataError.Remote.NO_INTERNET
        is ConnectException -> DataError.Remote.NO_INTERNET
        is SocketTimeoutException -> DataError.Remote.REQUEST_TIMEOUT
        else -> DataError.Remote.UNKNOWN
    }
}