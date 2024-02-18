package com.af.dentalla.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CardsDto(
    @SerializedName("cardId")
    val cardId: Int?,
    @SerializedName("currentUniversity")
    val currentUniversity: String?,
    @SerializedName("doctorName")
    val doctorName: String?,
    @SerializedName("doctorPhoto")
    val doctorPhoto: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("specialty")
    val specialty: Int?
)