package com.luminor.core.handler


import com.luminor.core.model.ResponseCallback
import com.luminor.core.model.AuthException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


inline fun <T> ResponseCallback<T>.launchCoroutineWithExceptionHandler(
    crossinline execute: suspend () -> T
) {

    val handler = CoroutineExceptionHandler { _, exception ->
        CoroutineScope(Dispatchers.Main).launch {
            this@launchCoroutineWithExceptionHandler.onError(
                when (exception) {
                    is AuthException -> exception
                    else -> AuthException.UnknownException(exception.message.toString())
                }
            )
        }
    }
    CoroutineScope(Dispatchers.IO + handler).launch {
        this@launchCoroutineWithExceptionHandler.onSuccess(execute())
    }

}