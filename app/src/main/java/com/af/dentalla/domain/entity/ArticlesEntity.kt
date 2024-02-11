package com.af.dentalla.domain.entity

import com.google.gson.annotations.SerializedName

data class ArticlesEntity(
    val articleId: Int,
    val content: String,
    val postingTime: String,
    val doctorName: String,
    val articleImage: String,
    val numberOfComments: Int,
    val title: String
)