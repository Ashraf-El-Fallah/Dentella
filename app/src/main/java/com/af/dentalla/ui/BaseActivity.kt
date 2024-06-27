package com.af.dentalla.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.utils.LocaleUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var dataStorePreferencesService: DataStorePreferencesService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val language = dataStorePreferencesService.getLanguage() ?: "en"
            LocaleUtils.setLocale(this@BaseActivity, language)
        }
    }
}