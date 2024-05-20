package com.af.dentalla.data.local

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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


    companion object {
        private const val TOKEN_KEY = "token"
        private const val PREFERENCES_FILE_NAME = "app"
        private const val LANGUAGE_KEY = "language"

    }
}

