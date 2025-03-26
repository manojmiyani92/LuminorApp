package com.luminor.interviewtest.screen

import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import com.luminor.core.impl.AuthProfile
import com.luminor.core.model.AuthException
import com.luminor.core.model.ResponseCallback
import com.luminor.core.model.UserDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class AuthViewmodel @Inject constructor(
    private val authProfile: AuthProfile
) : ViewModel() {


    private val _errorMessage = MutableStateFlow<AuthException>(AuthException.Nothing)
    var errorMessage = _errorMessage.asStateFlow()

    private val _userDetail = MutableStateFlow<UserDetail?>(null)
    var userDetail = _userDetail.asStateFlow()

    private val _isUserRegistered = MutableStateFlow(false)
    var isUserRegistered = _isUserRegistered.asStateFlow()

    private val _isUserLogoutSuccessfully = MutableStateFlow(false)
    var isUserLogoutSuccessfully = _isUserLogoutSuccessfully.asStateFlow()

    fun logout() {
        authProfile.logoutUseCase(object : ResponseCallback<Boolean> {
            override fun onSuccess(result: Boolean) {
                _isUserLogoutSuccessfully.value = result
            }

            override fun onError(e: AuthException) {
                _errorMessage.value = e
            }

        })
    }

    fun userLogin(email: String, password: String) {
        authProfile.loginUseCase(
            email = email,
            password = password,
            object : ResponseCallback<UserDetail> {
                override fun onSuccess(result: UserDetail) {
                    _userDetail.value = result
                }

                override fun onError(e: AuthException) {
                    _errorMessage.value = e
                }

            })
    }

    fun getUserById(id: Int) {
        authProfile.getUserById(id = id,
            object : ResponseCallback<UserDetail> {
                override fun onSuccess(result: UserDetail) {
                    _userDetail.value = result
                }

                override fun onError(e: AuthException) {
                    _errorMessage.value = e
                }

            })
    }

    fun autoLoginByValidSession() {
        authProfile.getUserByValidSession(object : ResponseCallback<UserDetail?> {
            override fun onSuccess(result: UserDetail?) {
                _userDetail.value = result
            }

            override fun onError(e: AuthException) {
                _errorMessage.value = e
            }

        })
    }

    fun userRegister(email: String, password: String, rePassword: String) {
        if (checkPasswordComplexity(email = email, password = password, rePassword = rePassword)) {
            authProfile.registerUseCase(
                email = email,
                password = password,
                object : ResponseCallback<Boolean> {
                    override fun onSuccess(result: Boolean) {
                        _isUserRegistered.value = result
                    }

                    override fun onError(e: AuthException) {
                        _errorMessage.value = e
                    }

                })
        }
    }

    fun checkPasswordComplexity(
        email: String,
        password: String,
        rePassword: String
    ): Boolean {
        return if (email.isEmpty()) {
            _errorMessage.value = AuthException.EmptyEmail()
            false
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorMessage.value = AuthException.InvalidEmail()
            false
        } else if (password.isEmpty()) {
            _errorMessage.value = AuthException.PasswordBlank()
            false
        } else if (rePassword.isEmpty()) {
            _errorMessage.value =
                AuthException.PasswordBlank("Re-enter password should not be empty")
            false
        } else if (password != rePassword) {
            _errorMessage.value = AuthException.PasswordMismatched()
            false
        } else {
            clearState()
            true
        }
    }

    fun clearState() {
        _errorMessage.value = AuthException.Nothing
        _isUserRegistered.value = false
    }

}