package com.af.dentalla.data.remote.requests

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
/*
TODO: use kotlix.serlization or Moshi (I think it's better than gson)
TODO: See (https://medium.com/@prakash.ayinala7/getting-started-with-room-database-in-kotlin-jetpack-compose-mvvm-dagger-hilt-3bdec10b70ed)
 */
data class DoctorProfileInformation(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("currentLevel")
    val currentLevel: String,
    @SerializedName("currentUniversity")
    val currentUniversity: String,
    @SerializedName("photo")
    val photo: MultipartBody.Part?
)
