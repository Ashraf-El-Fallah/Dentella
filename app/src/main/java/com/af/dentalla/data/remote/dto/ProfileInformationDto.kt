package com.af.dentalla.data.remote.dto


import com.google.gson.annotations.SerializedName
/*
TODO: use kotlix.serlization or Moshi (I think it's better than gson)
TODO: See (https://medium.com/@prakash.ayinala7/getting-started-with-room-database-in-kotlin-jetpack-compose-mvvm-dagger-hilt-3bdec10b70ed)
 */
data class ProfileInformationDto(
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