package com.af.dentalla.data.local

import kotlinx.coroutines.flow.Flow

interface DataStorePreferencesService {

    suspend fun saveTokenAndExpireDate(token: String?, expireDate: String)

    suspend fun getToken(): String

    suspend fun getExpireDate(): String
}