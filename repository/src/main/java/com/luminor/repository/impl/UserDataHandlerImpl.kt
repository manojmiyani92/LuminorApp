package com.luminor.repository.impl

import com.luminor.infrastructure.db.entities.User
import com.luminor.infrastructure.qulifier.Repository
import com.luminor.infrastructure.utils.passwordEncryption
import com.luminor.repository.AppDatabase
import javax.inject.Inject


interface UserDataHandler {
    suspend fun registerUser(user: User): Boolean
    suspend fun getUser(email: String, password: String): User
    suspend fun getUserById(id: Int): User?
    suspend fun getUserByValidSession(): User?
    suspend fun clearSession(): Boolean
}

class UserDataHandlerImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    @Repository private val storeModule: StoreModule
) : UserDataHandler {

    override suspend fun registerUser(user: User): Boolean {
        val isUserExist = appDatabase.userDao().checkUserExist(email = user.email)
        if (isUserExist) {
            throw Exception("User already existed!")
        } else {
            appDatabase.userDao().insertUser(user.asDbEntity())
        }
        return true
    }

    override suspend fun getUser(email: String, password: String): User {
        return appDatabase.userDao()
            .loadUser(email = email, password = password.passwordEncryption())?.let {
                storeModule.setUserSession(it.uid)
                it
            } ?: run { throw Exception("Invalid credentials!") }
    }

    override suspend fun getUserById(id: Int): User? {
        return appDatabase.userDao().loadUserById(id)
    }

    override suspend fun getUserByValidSession(): User? {
        return appDatabase.userDao()
            .loadUserById(storeModule.isSessionValid())
    }

    override suspend fun clearSession(): Boolean {
        storeModule.clearSession()
        return true
    }
}