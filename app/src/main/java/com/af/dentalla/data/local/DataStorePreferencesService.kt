package com.af.dentalla.data.local


interface DataStorePreferencesService {
    suspend fun saveToken(token: String?)
    suspend fun getToken(): String?
}