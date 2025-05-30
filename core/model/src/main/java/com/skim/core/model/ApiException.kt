package com.skim.core.model

class ApiException : RuntimeException {

    val code: Int

    constructor(code: Int) : super() {
        this.code = code
    }

    constructor(code: Int, s: String?) : super(s) {
        this.code = code
    }

    constructor(code: Int, message: String?, cause: Throwable?) : super(message, cause) {
        this.code = code
    }

    constructor(code: Int, cause: Throwable?) : super(cause) {
        this.code = code
    }
}