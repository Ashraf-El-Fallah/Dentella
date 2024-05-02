package com.af.dentalla.data.local

import kotlinx.coroutines.flow.Flow

interface DataStorePreferencesService {
    suspend fun saveToken(token: String?)
    suspend fun getToken(): String?

    suspend fun getExpireDate(): Flow<String?>
}