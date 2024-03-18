package com.af.dentalla.data.remote.dto


import com.google.gson.annotations.SerializedName

data class PostDtoItem(
    @SerializedName("content")
    val content: String?,
    @SerializedName("patientName")
    val patientName: String?,
    @SerializedName("patientPhoto")
    val patientPhoto: Any?,
    @SerializedName("postId")
    val postId: Int?
)