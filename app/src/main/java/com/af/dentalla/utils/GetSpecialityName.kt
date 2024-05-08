package com.af.dentalla.utils

fun getSpecialtyName(specialtyNumber: Int): String {
    return when (specialtyNumber) {
        0 -> "Cleaning"
        1 -> "Filling"
        2 -> "Crowns"
        3 -> "Implants"
        4 -> "Extraction"
        5 -> "Orthodontic"
        else -> "Unknown"
    }
}