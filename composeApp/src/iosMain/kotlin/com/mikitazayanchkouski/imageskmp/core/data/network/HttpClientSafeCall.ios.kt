package com.mikitazayanchkouski.imageskmp.core.data.network

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import io.ktor.client.engine.darwin.DarwinHttpRequestException
import platform.Foundation.NSError
import platform.Foundation.NSURLErrorCallIsActive
import platform.Foundation.NSURLErrorCannotFindHost
import platform.Foundation.NSURLErrorDNSLookupFailed
import platform.Foundation.NSURLErrorDataNotAllowed
import platform.Foundation.NSURLErrorDomain
import platform.Foundation.NSURLErrorInternationalRoamingOff
import platform.Foundation.NSURLErrorNetworkConnectionLost
import platform.Foundation.NSURLErrorNotConnectedToInternet
import platform.Foundation.NSURLErrorResourceUnavailable
import platform.Foundation.NSURLErrorTimedOut

actual fun platformErrorToDataError(error: Throwable): DataError.Remote {
    return when (error) {
        is DarwinHttpRequestException -> handleDarwinException(nsError = error.origin)
        else -> DataError.Remote.UNKNOWN
    }
}

private fun handleDarwinException(nsError: NSError): DataError.Remote {
    return if (nsError.domain == NSURLErrorDomain) {
        when (nsError.code) {
            NSURLErrorNotConnectedToInternet,
            NSURLErrorNetworkConnectionLost,
            NSURLErrorCannotFindHost,
            NSURLErrorDNSLookupFailed,
            NSURLErrorResourceUnavailable,
            NSURLErrorInternationalRoamingOff,
            NSURLErrorCallIsActive,
            NSURLErrorDataNotAllowed -> DataError.Remote.NO_INTERNET

            NSURLErrorTimedOut -> DataError.Remote.REQUEST_TIMEOUT
            else -> DataError.Remote.UNKNOWN
        }
    } else DataError.Remote.UNKNOWN
}