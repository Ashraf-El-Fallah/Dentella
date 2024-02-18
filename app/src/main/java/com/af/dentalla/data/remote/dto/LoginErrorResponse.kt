package com.af.dentalla.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginErrorResponse(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("title")
    val error: String?,
    @SerializedName("traceId")
    val traceId: String?,
    @SerializedName("type")
    val type: String?
)
