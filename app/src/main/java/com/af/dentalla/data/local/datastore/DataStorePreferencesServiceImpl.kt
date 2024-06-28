package com.af.dentalla.data.local.datastore

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.af.dentalla.utils.AccountManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePreferencesServiceImpl @Inject constructor(context: Application) :
    DataStorePreferencesService {
    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        PREFERENCES_FILE_NAME
    )
    private val prefDataStore = context.preferencesDataStore
    override suspend fun saveToken(token: String?) {
        Log.d("Token interceptor", "......")
        token?.let {
            prefDataStore.edit { preferences ->
                preferences[stringPreferencesKey(TOKEN_KEY)] = it
            }
        }
    }
    override suspend fun getToken(): String? {
        return prefDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(TOKEN_KEY)]
        }.firstOrNull()
    }

    override suspend fun clearToken() {
        prefDataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(TOKEN_KEY))
        }
    }

    override suspend fun saveLanguage(language: String) {
        prefDataStore.edit { preferences ->
            preferences[stringPreferencesKey(LANGUAGE_KEY)] = language
        }
    }

    override suspend fun getLanguage(): String? {
        return prefDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(LANGUAGE_KEY)]
        }.firstOrNull()
    }

    override suspend fun saveAccountType(accountType: AccountManager.AccountType) {
        prefDataStore.edit { preferences ->
            preferences[stringPreferencesKey(ACCOUNT_TYPE_KEY)] = accountType.name
        }
    }

    override suspend fun getAccountType(): AccountManager.AccountType? {
        val accountTypeString = prefDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(ACCOUNT_TYPE_KEY)]
        }.firstOrNull()
        return accountTypeString?.let { AccountManager.AccountType.valueOf(it) }
    }
    companion object {
        private const val TOKEN_KEY = "token"
        private const val PREFERENCES_FILE_NAME = "app"
        private const val LANGUAGE_KEY = "language"
        private const val ACCOUNT_TYPE_KEY = "account_type"
    }
}

