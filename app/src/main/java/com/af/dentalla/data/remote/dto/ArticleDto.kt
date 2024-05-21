package com.af.dentalla.data.remote.dto

import com.google.gson.annotations.SerializedName

/*
TODO: use kotlix.serlization or Moshi (I think it's better than gson)
TODO: See (https://medium.com/@prakash.ayinala7/getting-started-with-room-database-in-kotlin-jetpack-compose-mvvm-dagger-hilt-3bdec10b70ed)
 */
data class ArticleDto(
    @SerializedName("articleId")
    val articleId: Int?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("doctorName")
    val doctorName: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("numberOfComments")
    val numberOfComments: Int?,
    @SerializedName("numberOfLikes")
    val numberOfLikes: Int?,
    @SerializedName("title")
    val title: String?,
)