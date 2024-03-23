package com.af.dentalla.data.remote.requests


import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("content")
    val content: String?
)