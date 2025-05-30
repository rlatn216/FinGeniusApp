package com.skim.core.common.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val commonDispatcher: CommonDispatchers)

enum class CommonDispatchers {
    Default,
    IO
}