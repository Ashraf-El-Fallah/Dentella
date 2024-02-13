package com.af.dentalla.di.repository

import com.af.dentalla.data.repository.UserRepositoryImpl
import com.af.dentalla.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class PatientRepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindDoctorRepository(
        patientRepository: UserRepositoryImpl
    ): UserRepository
}