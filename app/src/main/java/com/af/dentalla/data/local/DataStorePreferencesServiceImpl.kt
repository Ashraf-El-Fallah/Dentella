package com.af.dentalla.data.local

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePreferencesServiceImpl @Inject constructor(context: Application) :
    DataStorePreferencesService {
    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        PREFERENCES_FILE_NAME
    )
    private val prefDataStore = context.preferencesDataStore

    override suspend fun saveToken(token: String?) {
        prefDataStore.edit { preferences ->
            preferences[stringPreferencesKey(TOKEN_KEY)] = token.toString()
        }
    }

    override fun getToken(): String {
        return prefDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(TOKEN_KEY)]
        }.toString()
    }

    companion object {
        private const val TOKEN_KEY = "token"
        private const val PREFERENCES_FILE_NAME = "app"
    }
}

