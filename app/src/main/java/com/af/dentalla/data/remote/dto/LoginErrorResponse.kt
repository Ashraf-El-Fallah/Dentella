package com.af.dentalla.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginErrorResponse(
    @SerializedName("error") val error: String
)
