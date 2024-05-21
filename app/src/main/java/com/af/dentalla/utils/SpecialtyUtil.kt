package com.af.dentalla.utils

import android.app.Application
import com.af.dentalla.R

object SpecialtyUtil {
    private lateinit var applicationContext: Application

    fun initialize(applicationContext: Application) {
        this.applicationContext = applicationContext
    }

    fun getSpecialtyName(specialtyNumber: Int): String {
        val resId = when (specialtyNumber) {
            0 -> R.string.cleaning
            1 -> R.string.filling
            2 -> R.string.crowns
            3 -> R.string.implants
            4 -> R.string.extraction
            5 -> R.string.dentures
            else -> return "Unknown"
        }
        return applicationContext.getString(resId)
    }
}