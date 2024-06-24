package com.af.dentalla.data.remote.requests


import com.google.gson.annotations.SerializedName

data class UserPasswords(
    @SerializedName("newPassword")
    val newPassword: String?,
    @SerializedName("oldPassword")
    val oldPassword: String?
)