package com.af.dentalla.data.remote.requests


import com.google.gson.annotations.SerializedName

data class DoctorPassword(
    @SerializedName("newPassword")
    val newPassword: String?,
    @SerializedName("oldPassword")
    val oldPassword: String?
)