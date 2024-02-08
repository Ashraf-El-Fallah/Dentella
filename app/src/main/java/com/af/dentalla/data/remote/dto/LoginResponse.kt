package com.af.dentalla.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("expiration")
    val tokenExpiration: String
)
