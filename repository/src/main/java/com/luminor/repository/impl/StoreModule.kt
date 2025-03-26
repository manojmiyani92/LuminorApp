package com.luminor.repository.impl

import android.content.SharedPreferences
import com.luminor.infrastructure.utils.clearData
import com.luminor.infrastructure.utils.intSharedPreferencesProperty
import javax.inject.Inject


interface StoreModule {
    fun isSessionValid(): Int
    fun setUserSession(id: Int)
    fun clearSession()
}

class StoreModuleImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : StoreModule {
    private var getUserId by sharedPreferences.intSharedPreferencesProperty(
        "userToken", -1
    )

    override fun isSessionValid(): Int = getUserId

    override fun setUserSession(id: Int) {
        getUserId = id

    }

    override fun clearSession() {
        sharedPreferences.clearData()
    }

}