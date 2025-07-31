package ca.ulaval.ima.mp.core.presentation.ui

import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.domain.utils.DataError

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(R.string.error_disk_full)
        DataError.Network.CLIENT -> UiText.StringResource(R.string.error_client)
        DataError.Network.PROCESS -> UiText.StringResource(R.string.error_process)
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.error_request_timeout)
        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(R.string.error_too_many_requests)
        DataError.Network.NO_INTERNET -> UiText.StringResource(R.string.error_no_internet)
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(R.string.error_payload_too_large)
        DataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.error_server_error)
        DataError.Network.SERIALIZATION -> UiText.StringResource(R.string.error_serialization)
        else -> UiText.StringResource(R.string.error_unknown)
    }
}
