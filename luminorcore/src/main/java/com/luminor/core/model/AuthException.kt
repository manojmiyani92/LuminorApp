package com.luminor.core.model

sealed class AuthException(errorMessage: String) : Exception(errorMessage) {
    data class InvalidEmail(val msg: String = "Email id is invalid") : AuthException(msg)
    data class EmptyEmail(val msg: String = "Email should not be empty!") : AuthException(msg)
    data class PasswordMismatched(val msg: String = "Password is mismatched") :
        AuthException(msg)

    data class PasswordComplexity(val msg: String = "Password is should be more than 5 char long.") :
        AuthException(msg)
    data class PasswordBlank(val msg: String = "Password should not be empty") :
        AuthException(msg)
    data class UnknownException(val msg: String = "Unknown Exception") :
        AuthException(msg)

    object Nothing : AuthException("")
}
