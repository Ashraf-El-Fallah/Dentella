package com.af.dentalla.data.remote.dto


import com.google.gson.annotations.SerializedName

data class UserProfileInformationDto(
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("currentUniversity")
    val currentUniversity: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("userName")
    val userName: String?
)