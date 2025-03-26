package com.luminor.repository.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.luminor.repository.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class PersistedModule {

    @Provides
    @Singleton
    fun roomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, DATABASE_NAME
        ).addMigrations(*AppDatabase.migrations).build()
    }

    companion object {
        private const val DATABASE_NAME = "luminor.db"
    }

    @Singleton
    @Provides
    fun sharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("luminor", MODE_PRIVATE)

}