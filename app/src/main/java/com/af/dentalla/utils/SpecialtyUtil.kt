package com.af.dentalla.utils

import android.content.Context
import com.af.dentalla.R
import java.util.Locale
fun getSpecialtyName(context: Context?, specialty: Any): String {
    val resId = when (specialty) {
        is Int -> {
            when (specialty) {
                0 -> R.string.cleaning
                1 -> R.string.filling
                2 -> R.string.crowns
                3 -> R.string.implants
                4 -> R.string.extraction
                5 -> R.string.orthodontic
                else -> R.string.unknown_specialty
            }
        }

        is String -> {
            when (specialty.lowercase(Locale.getDefault())) {
                "cleaning" -> R.string.cleaning
                "filling" -> R.string.filling
                "crowns" -> R.string.crowns
                "implants" -> R.string.implants
                "extraction" -> R.string.extraction
                "orthodontic" -> R.string.orthodontic
                else -> R.string.unknown_specialty
            }
        }

        else -> R.string.unknown_specialty
    }
    return context?.getString(resId) ?: ""
}
