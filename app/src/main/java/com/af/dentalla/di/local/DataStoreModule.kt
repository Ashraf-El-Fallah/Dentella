package com.af.dentalla.di.local

import com.af.dentalla.data.local.datastore.DataStorePreferencesService
import com.af.dentalla.data.local.datastore.DataStorePreferencesServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindsDataStorePreferences(
        dataStoreImpl: DataStorePreferencesServiceImpl
    ): DataStorePreferencesService
}