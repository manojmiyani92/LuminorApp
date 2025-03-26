package com.luminor.core.impl


import com.luminor.core.model.AuthException
import com.luminor.core.model.UserDetail
import com.luminor.infrastructure.db.entities.User
import com.luminor.repository.impl.UserDataHandler
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthProfileImplTest : BaseTest() {

    private lateinit var authProfile: AuthProfileImpl
    private lateinit var mockUserDataHandler: UserDataHandler
    private lateinit var user: User
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockUserDataHandler = mockk()
        authProfile = AuthProfileImpl(mockUserDataHandler)
        user = User(email = "test@example.com", password = "password", uid = 1)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `loginUseCase with success response`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password"
        val user = User(email = email, password = password, uid = 1)
        val expectedUserDetail = UserDetail(email = email, id = 1)
        // When
        coEvery { mockUserDataHandler.getUser(email, password) } returns user
        // Then
        authProfile.loginUseCase(email, password, callback(expectedUserDetail))
    }


    @Test
    fun `loginUseCase with invalid email throws Unknown exception`() = runTest {
        // Given
        val email = "dummy-invalid email"
        val password = "password123"
        // Then
        authProfile.loginUseCase(
            email,
            password,
            callback<UserDetail>(AuthException.InvalidEmail())
        )
    }

    @Test
    fun `registerUseCase with success response`() = runTest {
        // Given
        val expectedResult = true
        val actualResult = true
        // When
        coEvery { mockUserDataHandler.registerUser(user) } returns actualResult
        // Then
        authProfile.registerUseCase(
            email = user.email,
            password = user.password,
            callback<Boolean>(expectedResult)
        )

    }

    @Test
    fun `registerUseCase with already existed user response`() = runTest {
        // Given
        val expectedResult = false
        val actualResult = false
        // When
        coEvery { mockUserDataHandler.registerUser(user) } returns actualResult
        // Then
        authProfile.registerUseCase(user.email, user.password, callback<Boolean>(expectedResult))

    }


    @Test
    fun `valid Session with success response`() = runTest {
        // Given
        val expectedUserDetail = UserDetail(email = "test@example.com", id = 1)
        // When
        coEvery { mockUserDataHandler.getUserByValidSession() } returns user
        // Then
        authProfile.getUserByValidSession(callback<UserDetail?>(expectedUserDetail))
    }


    @Test
    fun `invalid Session response`() = runTest {
        // Given
        val user: User? = null
        val expectedUserDetail: UserDetail? = null
        // When
        coEvery { mockUserDataHandler.getUserByValidSession() } returns user
        // Then
        authProfile.getUserByValidSession(callback<UserDetail?>(expectedUserDetail))

    }

    @Test
    fun `invalid Session with unknown exception`() = runTest {
        // When
        coEvery { mockUserDataHandler.getUserByValidSession() } throws AuthException.UnknownException()
        // Then
        authProfile.getUserByValidSession(callback<UserDetail?>(AuthException.UnknownException()))

    }

    @Test
    fun `logout with success response`() = runTest {
        // When
        coEvery { mockUserDataHandler.clearSession() } returns true
        // Then
        authProfile.logoutUseCase(callback<Boolean>(true))
    }

    @Test
    fun `get User By Id with success response`() = runTest {
        // When
        val expectedUserDetail = UserDetail(email = "test@example.com", id = 1)
        coEvery { mockUserDataHandler.getUserById(1) } returns user
        // Then
        authProfile.getUserById(1, callback<UserDetail>(expectedUserDetail))

    }

}