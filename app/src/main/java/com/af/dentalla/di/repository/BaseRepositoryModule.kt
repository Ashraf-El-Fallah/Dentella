package com.af.dentalla.di.repository

import com.af.dentalla.data.repository.CommonRepositoryImpl
import com.af.dentalla.domain.repository.BaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class BaseRepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindBaseRepository(
        commonRepositoryImpl: CommonRepositoryImpl
    ):BaseRepository
}