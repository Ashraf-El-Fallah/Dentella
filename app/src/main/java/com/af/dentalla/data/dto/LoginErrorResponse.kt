package com.af.dentalla.data.dto

import com.google.gson.annotations.SerializedName

data class LoginErrorResponse(
    @SerializedName("error") val error: String
)
