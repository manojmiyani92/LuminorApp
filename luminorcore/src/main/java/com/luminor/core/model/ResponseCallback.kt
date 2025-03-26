package com.luminor.core.model



interface ResponseCallback<RESULT> {
    fun onSuccess(result: RESULT)
    fun onError(e: AuthException)
}
