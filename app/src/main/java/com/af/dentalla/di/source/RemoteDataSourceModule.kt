package com.af.dentalla.di.source

import com.af.dentalla.data.source.remote.RemoteDataSource
import com.af.dentalla.data.source.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    @ViewModelScoped
    abstract fun bindDataSource(
        dataSource: RemoteDataSourceImpl
    ): RemoteDataSource
}