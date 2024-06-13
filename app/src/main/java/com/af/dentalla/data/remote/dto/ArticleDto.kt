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
    val imageUrl: String?,
    @SerializedName("numberOfComments")
    val numberOfComments: Int?,
    @SerializedName("numberOfLikes")
    val numberOfLikes: Int?,
    @SerializedName("title")
    val title: String?
)