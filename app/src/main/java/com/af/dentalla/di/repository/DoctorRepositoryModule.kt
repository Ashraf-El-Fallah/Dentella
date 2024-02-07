package com.af.dentalla.di.repository

import com.af.dentalla.data.repository.DoctorRepositoryImpl
import com.af.dentalla.domain.repository.DoctorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DoctorRepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindDoctorRepository(
        doctorRepository: DoctorRepositoryImpl
    ): DoctorRepository
}