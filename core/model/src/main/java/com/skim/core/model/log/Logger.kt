package com.skim.core.model.log

interface Logger {

    companion object {
        const val TAG = "FinGenius"
    }

    fun v(message: String, tag: String? = TAG)

    fun d(message: String, tag: String? = TAG)

    fun i(message: String, tag: String? = TAG)

    fun w(message: String, tag: String? = TAG)

    fun w(message: String, tr: Throwable, tag: String? = TAG)

    fun w(tr: Throwable, tag: String? = TAG)

    fun e(message: String, tag: String? = TAG)

    fun e(message: String, tr: Throwable?, tag: String? = TAG)

    fun e(tr: Throwable, tag: String? = TAG)

    fun json(json: String, tag: String? = TAG)

}