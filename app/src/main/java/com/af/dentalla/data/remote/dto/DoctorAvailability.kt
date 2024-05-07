package com.af.dentalla.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DoctorAvailability(
    @SerializedName("availableDates")
    val availableDates: List<String?>?
)