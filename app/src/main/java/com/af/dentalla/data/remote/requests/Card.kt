package com.af.dentalla.data.remote.requests


import com.af.dentalla.data.remote.dto.DoctorAvailability
import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("doctorAvailability")
    val doctorAvailability: DoctorAvailability?,
    @SerializedName("specialty")
    val specialty: Int?
)