package com.af.dentalla.data.remote.requests


import com.google.gson.annotations.SerializedName

data class AddArticle(
    @SerializedName("content")
    val content: String?,
    @SerializedName("imageData")
    val imageData: String? = null,
    @SerializedName("title")
    val title: String? = null
)