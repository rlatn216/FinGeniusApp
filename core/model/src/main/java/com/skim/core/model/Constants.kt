package com.skim.core.model

object WebSendStatus {
    const val WEB_SEND_STATUS_CANCEL = -999
    const val WEB_SEND_STATUS_ERROR = 500
    const val WEB_SEND_STATUS_SUCCESS = 200
}

object ExceptionCode {
    const val EXCEPTION_CODE_NOT_AVAILABLE_NETWORK = -99991
    const val EXCEPTION_CODE_FAIL_API = -99992
    const val EXCEPTION_CODE_SOCKET_TIMEOUT = -99996
    const val EXCEPTION_CODE_OTP_TIMEOUT = -99997
    const val EXCEPTION_CODE_INTERNAL_CONNECTION = -99998
    const val EXCEPTION_CODE_INTERNAL_UNKNOWN = -99999
}

object ApiErrorCode {
    const val ERROR_CODE_UNREGISTERED_DEVICE = 406
    const val ERROR_CODE_OTP_TIMEOUT = 451
    const val ERROR_STATUS_INVALID_TOKEN = 900
}

object HeaderKeySet {
    const val ACCESS_TOKEN = "Access-Token"
    const val SET_COOKIE = "Set-Cookie"
}
