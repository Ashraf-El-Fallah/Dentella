package com.af.dentalla.data.dto

import com.google.gson.annotations.SerializedName

data class SignUpErrorResponse(
    @SerializedName("code") val message: String,
    @SerializedName("description") val description: String
)
