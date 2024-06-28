package com.af.dentalla.data.local.datastore

import com.af.dentalla.utils.AccountManager


interface DataStorePreferencesService {
    suspend fun saveToken(token: String?)
    suspend fun getToken(): String?
    suspend fun clearToken()
    suspend fun saveLanguage(language: String)
    suspend fun getLanguage(): String?
    suspend fun saveAccountType(accountType: AccountManager.AccountType)
    suspend fun getAccountType(): AccountManager.AccountType?
}