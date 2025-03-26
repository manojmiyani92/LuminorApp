package com.luminor.core.impl

import com.luminor.core.model.AuthException
import com.luminor.core.model.ResponseCallback
import org.junit.Assert.assertEquals

open class BaseTest {

    inline fun <reified T> callback(exactedResult: T): ResponseCallback<T> {
        return object : ResponseCallback<T> {
            override fun onSuccess(result: T) {
                assertEquals(exactedResult, result)
            }

            override fun onError(e: AuthException) {

            }
        }
    }


    inline fun <reified T> callback(exactedException: AuthException): ResponseCallback<T> {
        return object : ResponseCallback<T> {
            override fun onSuccess(result: T) {

            }

            override fun onError(e: AuthException) {
                assertEquals(exactedException, e)
            }
        }
    }
}