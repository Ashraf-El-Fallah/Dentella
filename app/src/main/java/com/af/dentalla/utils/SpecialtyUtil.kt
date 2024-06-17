package com.af.dentalla.utils

import android.content.Context
import com.af.dentalla.R

fun getSpecialtyName(context: Context?, specialtyNumber: Int): String {
    val resId = when (specialtyNumber) {
        0 -> R.string.cleaning
        1 -> R.string.filling
        2 -> R.string.crowns
        3 -> R.string.implants
        4 -> R.string.extraction
        5 -> R.string.dentures
        else -> return "Unknown"
    }
    return context?.getString(resId) ?: ""
}
