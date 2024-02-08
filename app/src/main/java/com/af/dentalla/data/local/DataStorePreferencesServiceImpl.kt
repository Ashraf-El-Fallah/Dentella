package com.af.dentalla.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferencesServiceImpl(context: Context) : DataStorePreferencesService {
    private val Context.preferencesDataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
        PREFERENCES_FILE_NAME
    )
    private val prefDataStore = context.preferencesDataStore
    override suspend fun saveTokenAndExpireDate(token: String, expireDate: String) {
        prefDataStore.edit { preferences ->
            preferences[stringPreferencesKey(TOKEN_KEY)] = token
            preferences[stringPreferencesKey(EXPIRY_KEY)] = expireDate
        }
    }

    override suspend fun getToken(): Flow<String?> {
        return prefDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(TOKEN_KEY)]
        }
    }

    override suspend fun getExpireDate(): Flow<String?> {
        return prefDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(EXPIRY_KEY)]
        }
    }

    companion object {
        private const val TOKEN_KEY = "token"
        private const val EXPIRY_KEY = "expiry"
        private const val PREFERENCES_FILE_NAME = "app"

    }
}

