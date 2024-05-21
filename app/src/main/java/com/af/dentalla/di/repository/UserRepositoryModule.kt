package com.af.dentalla.di.repository

import com.af.dentalla.data.repository.UserRepositoryImpl
import com.af.dentalla.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


// TODO: you can make UserRepositoryModule singleton if you use it through the whole app
@Module
@InstallIn(ViewModelComponent::class)
abstract class UserRepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository
}