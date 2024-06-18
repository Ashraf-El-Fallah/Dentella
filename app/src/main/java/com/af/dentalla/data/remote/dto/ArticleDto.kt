package com.af.dentalla.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ArticleDto(
    @SerializedName("articleId")
    val articleId: Int?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("doctorName")
    val doctorName: String?,
    @SerializedName("photo")
    val doctorImage: String?,
    @SerializedName("imageData")
    val articleImage: String?,
    @SerializedName("title")
    val title: String?
)