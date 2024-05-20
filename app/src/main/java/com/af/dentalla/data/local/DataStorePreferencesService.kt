package com.af.dentalla.data.local


interface DataStorePreferencesService {
    suspend fun saveToken(token: String?)
    suspend fun getToken(): String?
    suspend fun clearToken()
    suspend fun saveLanguage(language: String)
    suspend fun getLanguage(): String?
}