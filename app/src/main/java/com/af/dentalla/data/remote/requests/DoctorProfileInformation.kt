package com.af.dentalla.data.remote.requests

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

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
    val photo: MultipartBody.Part
)
