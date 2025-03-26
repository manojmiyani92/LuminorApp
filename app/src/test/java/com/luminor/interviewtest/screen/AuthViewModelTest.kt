package com.luminor.interviewtest.screen

import com.luminor.core.impl.AuthProfile
import com.luminor.core.model.ResponseCallback
import com.luminor.core.model.AuthException
import com.luminor.core.model.UserDetail
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthViewModelTest {


    private lateinit var viewModel: AuthViewmodel
    private lateinit var authProfile: AuthProfile
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        authProfile = mockk(relaxed = true)
        viewModel = AuthViewmodel(authProfile)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `user with success logout response`() = runTest(testDispatcher) {
            // Arrange
            coEvery { authProfile.logoutUseCase(any()) } answers {
                val callback = firstArg<ResponseCallback<Boolean>>()
                callback.onSuccess(true)
            }

            // Act
            viewModel.logout()

            // Assert
            assertTrue(viewModel.isUserLogoutSuccessfully.first())
        }

    @Test
    fun `logout with unknown exception on error`() = runTest(testDispatcher) {
        // Given
        val expectedException = AuthException.UnknownException()
        coEvery { authProfile.logoutUseCase(any()) } answers {
            val callback = firstArg<ResponseCallback<Boolean>>()
            callback.onError(expectedException)
        }

        // When
        viewModel.logout()

        // Then
        assertEquals(expectedException, viewModel.errorMessage.first())
    }

    @Test
    fun `userLogin success with success response`() = runTest(testDispatcher) {
        // Given
        val expectedUserDetail = UserDetail(email = "test@example.com", id = 1)
        // When
        coEvery { authProfile.loginUseCase(any(), any(), any()) } answers {
            val callback = thirdArg<ResponseCallback<UserDetail>>()
            callback.onSuccess(expectedUserDetail)
        }
        viewModel.userLogin("test@example.com", "password")
        // Then
        assertEquals(expectedUserDetail, viewModel.userDetail.first())
    }

    @Test
    fun `userLogin with invalid email exception`() = runTest(testDispatcher) {
        // Given
        val expectedException = AuthException.InvalidEmail()
        //When
        coEvery { authProfile.loginUseCase(any(), any(), any()) } answers {
            val callback = thirdArg<ResponseCallback<UserDetail>>()
            callback.onError(expectedException)
        }
        // Then
        viewModel.userLogin("dummy-email.com", "wrongpassword")
        // Assert
        assertEquals(expectedException, viewModel.errorMessage.first())
    }

    @Test
    fun `getUserById with success`() = runTest(testDispatcher) {
        // Given
        val expectedUserDetail = UserDetail(email = "test@example.com", id = 12)
        //When
        coEvery { authProfile.getUserById(any(), any()) } answers {
            val callback = secondArg<ResponseCallback<UserDetail>>()
            callback.onSuccess(expectedUserDetail)
        }

        // Then
        viewModel.getUserById(1)

        // Assert
        assertEquals(expectedUserDetail, viewModel.userDetail.first())
    }

    @Test
    fun `getUserById should set errorMessage on error`() = runTest(testDispatcher) {
        // Given
        val expectedException = AuthException.UnknownException()
        // When
        coEvery { authProfile.getUserById(any(), any()) } answers {
            val callback = secondArg<ResponseCallback<UserDetail>>()
            callback.onError(expectedException)
        }
        // Then
        viewModel.getUserById(-1)

        // Assert
        assertEquals(expectedException, viewModel.errorMessage.first())
    }

    @Test
    fun `autoLoginByValidSession with success`() = runTest(testDispatcher) {
        // Given
        val expectedUserDetail = UserDetail(email = "test@example.com", id = 1)
        // When
        coEvery { authProfile.getUserByValidSession(any()) } answers {
            val callback = firstArg<ResponseCallback<UserDetail?>>()
            callback.onSuccess(expectedUserDetail)
        }
        // Then
        viewModel.autoLoginByValidSession()

        // Assert
        assertEquals(expectedUserDetail, viewModel.userDetail.first())
    }

    @Test
    fun `autoLoginByValidSession with user not found error`() = runTest(testDispatcher) {
        // Given
        val expectedException = AuthException.UnknownException("User not found!")
        // When
        coEvery { authProfile.getUserByValidSession(any()) } answers {
            val callback = firstArg<ResponseCallback<UserDetail?>>()
            callback.onError(expectedException)
        }

        // Then
        viewModel.autoLoginByValidSession()

        // Assert
        assertEquals(expectedException, viewModel.errorMessage.first())
    }

    @Test
    fun `userRegister with success`() = runTest(testDispatcher) {
        // When
        coEvery { authProfile.registerUseCase(any(), any(), any()) } answers {
            val callback = thirdArg<ResponseCallback<Boolean>>()
            callback.onSuccess(true)
        }
        // Then
        viewModel.userRegister("test@example.com", "password", "password")
        // Assert
        assertTrue(viewModel.isUserRegistered.first())

    }

    @Test
    fun `userRegister with exception`() = runTest(testDispatcher) {
        // Given
        val expectedException = AuthException.UnknownException("User is already existed!")
        // When
        coEvery { authProfile.registerUseCase(any(), any(), any()) } answers {
            val callback = thirdArg<ResponseCallback<Boolean>>()
            callback.onError(expectedException)
        }

        // Then
        viewModel.userRegister("test@example.com", "password", "password")


        // Assert
        assertEquals(expectedException, viewModel.errorMessage.first())

    }

    @Test
    fun `user with valid credentials`() = runTest(testDispatcher) {
        viewModel.checkPasswordComplexity("manojmiyani99@gmail.com", "password123", "password123")
        assertEquals(AuthException.Nothing, viewModel.errorMessage.first())
    }

    @Test
    fun `user with invalid email`() = runTest(testDispatcher) {
        viewModel.checkPasswordComplexity("invalid-email", "password123", "password123")
        assertEquals(AuthException.InvalidEmail(), viewModel.errorMessage.first())
    }

    @Test
    fun `user with password mismatch`() = runTest(testDispatcher) {
        viewModel.checkPasswordComplexity("manoj@gmail.com", "1212", "password123")
        assertEquals(AuthException.PasswordMismatched(), viewModel.errorMessage.first())
    }

    @Test
    fun `user with password empty`() = runTest(testDispatcher) {
        viewModel.checkPasswordComplexity("manoj@gmail.com", "", "1212")
        assertEquals(AuthException.PasswordBlank(), viewModel.errorMessage.first())
    }
}