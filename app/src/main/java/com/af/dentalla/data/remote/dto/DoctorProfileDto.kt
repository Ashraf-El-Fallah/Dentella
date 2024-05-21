package com.af.dentalla.data.remote.dto

import com.google.gson.annotations.SerializedName

/*
TODO: use kotlix.serlization or Moshi (I think it's better than gson)
TODO: See (https://medium.com/@prakash.ayinala7/getting-started-with-room-database-in-kotlin-jetpack-compose-mvvm-dagger-hilt-3bdec10b70ed)
 */
data class DoctorProfileDto(
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("currentUniversity")
    val currentUniversity: String?,
    @SerializedName("doctorAvailability")
    val doctorAvailability: List<DoctorAvailability>?,
    @SerializedName("doctorName")
    val doctorName: String?,
    @SerializedName("doctorPhoto")
    val doctorPhoto: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("specialty")
    val specialty: Int?,
)