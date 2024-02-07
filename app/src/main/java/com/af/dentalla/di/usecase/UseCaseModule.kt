package com.af.dentalla.di.usecase

import com.af.dentalla.domain.usecase.doctor.login.LoginDoctorUseCase
import com.af.dentalla.domain.usecase.doctor.login.LoginDoctorUseCaseImpl
import com.af.dentalla.domain.usecase.doctor.signup.SignUpDoctorUseCase
import com.af.dentalla.domain.usecase.doctor.signup.SignUpDoctorUseCaseImpl
import com.af.dentalla.domain.usecase.patient.login.LoginPatientUseCase
import com.af.dentalla.domain.usecase.patient.login.LoginPatientUseCaseImpl
import com.af.dentalla.domain.usecase.patient.signup.SignUpPatientUseCase
import com.af.dentalla.domain.usecase.patient.signup.SignUpPatientUseCaseImpl
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
        loginUseCaseImpl: LoginPatientUseCaseImpl
    ): LoginPatientUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindLoginInDoctorUseCase(
        loginUseCaseImpl: LoginDoctorUseCaseImpl
    ): LoginDoctorUseCase


    @Binds
    @ViewModelScoped
    abstract fun bindSignUpPatientUseCase(
        signUpUseCaseImpl: SignUpPatientUseCaseImpl
    ): SignUpPatientUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSignUpDoctorUseCase(
        signUpUseCaseImpl: SignUpDoctorUseCaseImpl
    ): SignUpDoctorUseCase
}