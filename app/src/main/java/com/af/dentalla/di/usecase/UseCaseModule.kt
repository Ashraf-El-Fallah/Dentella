package com.af.dentalla.di.usecase

import com.af.dentalla.domain.user.login.LoginUseCase
import com.af.dentalla.domain.user.login.LoginUseCaseImpl
import com.af.dentalla.domain.user.signup.SignUpUseCase
import com.af.dentalla.domain.user.signup.SignUpUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLoginInUseCase(
        loginUseCaseImpl: LoginUseCaseImpl
    ): LoginUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSignUpUseCase(
        signUpUseCaseImpl: SignUpUseCaseImpl
    ): SignUpUseCase
}