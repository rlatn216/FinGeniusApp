package com.skim.core.model

import com.skim.core.model.log.EventLogger
import com.skim.core.model.log.Logger


object BaseLog : Logger, EventLogger {

    var logger: Logger? = null

    var eventLogger: EventLogger? = null

    override fun event(name: String, params: Map<String, Any>) {
        eventLogger?.event(name, params)
    }

    override fun exception(throwable: Throwable) {
        eventLogger?.exception(throwable)
    }

    override fun v(message: String, tag: String?) {
        if (!BaseConfig.DEBUG_LOG) return
        logger?.v(message, tag)
    }

    override fun d(message: String, tag: String?) {
        if (!BaseConfig.DEBUG_LOG) return
        logger?.d(message, tag)
    }

    override fun i(message: String, tag: String?) {
        logger?.i(message, tag)
    }

    override fun w(message: String, tag: String?) {
        logger?.w(message, tag)
    }

    override fun w(message: String, tr: Throwable, tag: String?) {
        logger?.w(message, tr, tag)
    }

    override fun w(tr: Throwable, tag: String?) {
        logger?.w(tr, tag)
    }

    override fun e(message: String, tag: String?) {
        logger?.e(message, tag)
    }

    override fun e(message: String, tr: Throwable?, tag: String?) {
        logger?.e(message, tr, tag)
    }

    override fun e(tr: Throwable, tag: String?) {
        logger?.e(tr, tag)
    }

    override fun json(json: String, tag: String?) {
        if (!BaseConfig.DEBUG_LOG) return
        logger?.json(json, tag)
    }

}