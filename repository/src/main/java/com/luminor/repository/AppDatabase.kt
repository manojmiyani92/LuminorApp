package com.luminor.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.luminor.infrastructure.db.entities.User
import com.luminor.repository.dao.UserDao

@Database(
    entities = [User::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    companion object {
        val migrations = arrayOf<Migration>(

        )
    }

    abstract fun userDao(): UserDao
}