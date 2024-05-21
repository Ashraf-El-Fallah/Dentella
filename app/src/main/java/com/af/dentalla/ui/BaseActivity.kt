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

        //TODO: Do we need every time to change the local?
        // try to find an alternative way and remove BaseActivity if not necessary
        lifecycleScope.launch {
            val language = dataStorePreferencesService.getLanguage() ?: "en"
            LocaleUtils.setLocale(this@BaseActivity, language)
        }
    }
}