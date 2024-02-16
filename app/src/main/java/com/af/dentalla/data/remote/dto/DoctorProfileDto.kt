package com.af.dentalla.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DoctorProfileDto(
    @SerializedName("bio")
    val bio: String,
    @SerializedName("currentUniversity")
    val currentUniversity: String,
    @SerializedName("doctorAvailability")
    val doctorAvailability: List<String>,
    @SerializedName("doctorName")
    val doctorName: String,
    @SerializedName("doctorPhoto")
    val doctorPhoto: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("specialty")
    val specialty: Int,
//    @SerializedName("availableDates")
//    val availableDates: List<String>
)