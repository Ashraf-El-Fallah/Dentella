package com.af.dentalla.data.local

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
//        prefDataStore.edit { preferences ->
//            preferences[stringPreferencesKey(TOKEN_KEY)] = token.toString()
//        }
        token?.let {
            prefDataStore.edit { preferences ->
                preferences[stringPreferencesKey(TOKEN_KEY)] = it
            }
        }
    }

    override fun getToken(): String {
        return prefDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(TOKEN_KEY)]
        }.toString()
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

