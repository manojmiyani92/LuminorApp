package com.luminor.core.di

import com.luminor.core.impl.AuthProfile
import com.luminor.core.impl.AuthProfileImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class AuthModule {

    @Binds
    internal abstract fun getImageImpl(imageAuthorProfileImpl: AuthProfileImpl): AuthProfile
}