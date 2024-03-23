package com.af.dentalla.data.remote.requests


import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("content")
    val content: String?,
    @SerializedName("imageData")
    val imageData: String?,
    @SerializedName("title")
    val title: String?
)