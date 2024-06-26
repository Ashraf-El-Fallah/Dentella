package com.af.dentalla.utils

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.annotation.StringRes
import java.util.Locale

object GetStringUtil {
    fun getString(application: Application, @StringRes resId: Int, language: String): String {
        val localeContext = setLocale(application, language)
        return localeContext.getString(resId)
    }

    private fun setLocale(application: Application, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        return application.createConfigurationContext(config)
    }
}