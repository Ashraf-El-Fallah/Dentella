package com.af.dentalla.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("message") val message: String
)
