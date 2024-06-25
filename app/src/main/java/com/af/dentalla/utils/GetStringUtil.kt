package com.af.dentalla.utils

import android.app.Application
import androidx.annotation.StringRes

object GetStringUtil {
    fun getString(application: Application, @StringRes resId: Int): String {
        return application.getString(resId)
    }
}