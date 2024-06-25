package com.af.dentalla.di.source

import com.af.dentalla.data.source.remote.RemoteDataSource
import com.af.dentalla.data.source.remote.RemoteDateSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RemoteDateSourceModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRemoteDataStore(
        dataSource: RemoteDateSourceImpl
    ): RemoteDataSource
}