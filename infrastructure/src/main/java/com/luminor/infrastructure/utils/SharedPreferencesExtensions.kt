package com.luminor.infrastructure.utils

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Int property that is stored directly into the given [SharedPreferences]
 */
fun SharedPreferences.intSharedPreferencesProperty(
    key: String,
    defaultValue: Int
): ReadWriteProperty<Any?, Int> {
    return sharedPreferencesProperty(
        key,
        defaultValue,
        SharedPreferences::getInt,
        SharedPreferences.Editor::putInt
    )
}


inline fun <T> SharedPreferences.sharedPreferencesProperty(
    key: String,
    defaultValue: T,
    crossinline retrieve: SharedPreferences.(String, T) -> T,
    crossinline put: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
): ReadWriteProperty<Any?, T> {
    return object : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return retrieve(key, defaultValue)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            edit().put(key, value).apply()
        }
    }
}

fun SharedPreferences.clearData() = edit().clear().apply()

