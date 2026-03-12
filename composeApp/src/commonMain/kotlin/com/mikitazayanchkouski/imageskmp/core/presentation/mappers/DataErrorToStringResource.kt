package com.mikitazayanchkouski.imageskmp.core.presentation.mappers

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.error_local_file_not_found
import imageskmp.composeapp.generated.resources.error_local_storage_is_full
import imageskmp.composeapp.generated.resources.error_remote_bad_request
import imageskmp.composeapp.generated.resources.error_remote_conflict
import imageskmp.composeapp.generated.resources.error_remote_forbidden
import imageskmp.composeapp.generated.resources.error_remote_json_conversion
import imageskmp.composeapp.generated.resources.error_remote_no_internet
import imageskmp.composeapp.generated.resources.error_remote_not_found
import imageskmp.composeapp.generated.resources.error_remote_payload_too_large
import imageskmp.composeapp.generated.resources.error_remote_redirect
import imageskmp.composeapp.generated.resources.error_remote_request_timeout
import imageskmp.composeapp.generated.resources.error_remote_serialization
import imageskmp.composeapp.generated.resources.error_remote_server_error
import imageskmp.composeapp.generated.resources.error_remote_server_unavailable
import imageskmp.composeapp.generated.resources.error_remote_too_many_requests
import imageskmp.composeapp.generated.resources.error_remote_unauthorized
import imageskmp.composeapp.generated.resources.error_unknown
import org.jetbrains.compose.resources.StringResource

fun DataError.mapToStringResource(): StringResource {
    return when (this) {
        DataError.Local.STORAGE_IS_FULL -> Res.string.error_local_storage_is_full
        DataError.Local.FILE_NOT_FOUND -> Res.string.error_local_file_not_found
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.BAD_REQUEST -> Res.string.error_remote_bad_request
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_remote_request_timeout
        DataError.Remote.UNAUTHORIZED -> Res.string.error_remote_unauthorized
        DataError.Remote.FORBIDDEN -> Res.string.error_remote_forbidden
        DataError.Remote.NOT_FOUND -> Res.string.error_remote_not_found
        DataError.Remote.CONFLICT -> Res.string.error_remote_conflict
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_remote_too_many_requests
        DataError.Remote.NO_INTERNET -> Res.string.error_remote_no_internet
        DataError.Remote.PAYLOAD_TOO_LARGE -> Res.string.error_remote_payload_too_large
        DataError.Remote.SERVER_ERROR -> Res.string.error_remote_server_error
        DataError.Remote.SERVER_UNAVAILABLE -> Res.string.error_remote_server_unavailable
        DataError.Remote.SERIALIZATION -> Res.string.error_remote_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.REDIRECT -> Res.string.error_remote_redirect
        DataError.Remote.JSON_CONVERSION -> Res.string.error_remote_json_conversion
    }
}