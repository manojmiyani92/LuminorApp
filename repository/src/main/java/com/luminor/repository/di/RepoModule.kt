package com.luminor.repository.di

import com.luminor.infrastructure.qulifier.Repository
import com.luminor.repository.impl.StoreModule
import com.luminor.repository.impl.StoreModuleImpl
import com.luminor.repository.impl.UserDataHandler
import com.luminor.repository.impl.UserDataHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class RepoModule {
    @Binds
    @Repository
    abstract fun getUserDataHandler(userDataHandlerImpl: UserDataHandlerImpl): UserDataHandler

    @Binds
    @Repository
    abstract fun storeModule(storeModuleImpl: StoreModuleImpl): StoreModule
}