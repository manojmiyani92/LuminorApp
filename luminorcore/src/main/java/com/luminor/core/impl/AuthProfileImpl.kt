package com.luminor.core.impl

import com.luminor.core.handler.launchCoroutineWithExceptionHandler
import com.luminor.core.model.AuthException
import com.luminor.core.model.ResponseCallback
import com.luminor.core.model.UserDetail
import com.luminor.infrastructure.db.entities.User
import com.luminor.infrastructure.qulifier.Repository
import com.luminor.infrastructure.utils.isValidEmail
import com.luminor.repository.impl.UserDataHandler
import javax.inject.Inject

interface AuthProfile {
    fun loginUseCase(
        email: String,
        password: String,
        responseCallback: ResponseCallback<UserDetail>
    )

    fun getUserById(
        id: Int,
        responseCallback: ResponseCallback<UserDetail>
    )

    fun registerUseCase(
        email: String,
        password: String,
        responseCallback: ResponseCallback<Boolean>
    )

    fun getUserByValidSession(
        responseCallback: ResponseCallback<UserDetail?>
    )

    fun logoutUseCase(
        responseCallback: ResponseCallback<Boolean>
    )

}

internal class AuthProfileImpl @Inject constructor(
    @Repository private val userDataHandler: UserDataHandler,
) : AuthProfile {

    override fun loginUseCase(
        email: String,
        password: String,
        responseCallback: ResponseCallback<UserDetail>
    ) {
        responseCallback.launchCoroutineWithExceptionHandler {
            validateUserCredentials(email, password)
            userDataHandler.getUser(email = email, password = password).mapUserDetail()
        }
    }

    override fun getUserById(id: Int, responseCallback: ResponseCallback<UserDetail>) {
        responseCallback.launchCoroutineWithExceptionHandler {
            userDataHandler.getUserById(id = id)?.mapUserDetail() ?: run {
                throw AuthException.UnknownException("User not found!")
            }
        }
    }

    override fun registerUseCase(
        email: String,
        password: String,
        responseCallback: ResponseCallback<Boolean>
    ) {
        responseCallback.launchCoroutineWithExceptionHandler {
            validateUserCredentials(email, password)
            userDataHandler.registerUser(User(email = email, password = password))
        }
    }

    override fun getUserByValidSession(responseCallback: ResponseCallback<UserDetail?>) {
        responseCallback.launchCoroutineWithExceptionHandler {
            userDataHandler.getUserByValidSession()?.mapUserDetail()
        }
    }

    override fun logoutUseCase(responseCallback: ResponseCallback<Boolean>) {
        responseCallback.launchCoroutineWithExceptionHandler {
            userDataHandler.clearSession()
        }
    }

    fun validateUserCredentials(
        email: String,
        password: String
    ) {
        if (email.isEmpty()) {
            throw AuthException.EmptyEmail()
        } else if (!email.isValidEmail()) {
            throw AuthException.InvalidEmail()
        } else if (password.isEmpty()) {
            throw AuthException.PasswordBlank()
        } else if (password.length < 6) {
            throw AuthException.PasswordComplexity()
        }
    }


    private fun User.mapUserDetail(): UserDetail = UserDetail(id = this.uid, email = this.email)

}
