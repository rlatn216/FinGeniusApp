package com.skim.core.model.log

interface EventLogger {

    fun event(name: String, params: Map<String, Any>)

    fun exception(throwable: Throwable)

    object Event {
        const val SCREEN_VIEW = "screen_view"
    }

    object Param {
        const val SCREEN_CLASS = "screen_class"
        const val SCREEN_NAME = "screen_name"
    }

}