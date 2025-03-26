package com.luminor.core.handler

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface AppDispatchers {

    @Suppress("PropertyName")
    val Default: CoroutineContext
    @Suppress("PropertyName")
    val Main: CoroutineContext
    @Suppress("PropertyName")
    val IO: CoroutineContext
}

class AppDispatchersImpl @Inject constructor() : AppDispatchers {
    override val Default: CoroutineContext
        get() = Dispatchers.Default
    override val Main: CoroutineContext
        get() = Dispatchers.Main
    override val IO: CoroutineContext
        get() = Dispatchers.IO
}