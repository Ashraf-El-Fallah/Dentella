package com.af.dentalla.ui

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.af.dentalla.R
import com.af.dentalla.data.local.datastore.DataStorePreferencesService
import com.af.dentalla.utils.LocaleUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var dataStorePreferencesService: DataStorePreferencesService
    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashForALlVersions()
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val language = dataStorePreferencesService.getLanguage() ?: "en"
            LocaleUtils.setLocale(this@BaseActivity, language)
        }
    }

    private fun initSplashForALlVersions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            initSplashScreen()
        } else {
            setTheme(R.style.Theme_Dentalla)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    open fun initSplashScreen() {
        installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView, View.TRANSLATION_Y, 0f, -splashScreenView.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 1000L

            slideUp.doOnEnd { splashScreenView.remove() }
            slideUp.start()
        }
    }
}