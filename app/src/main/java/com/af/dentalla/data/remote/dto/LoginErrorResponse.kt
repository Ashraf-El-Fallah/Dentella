package com.af.dentalla.data.remote.dto

import com.google.gson.annotations.SerializedName

/*
TODO: use kotlix.serlization or Moshi (I think it's better than gson)
TODO: See (https://medium.com/@prakash.ayinala7/getting-started-with-room-database-in-kotlin-jetpack-compose-mvvm-dagger-hilt-3bdec10b70ed)
 */
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
