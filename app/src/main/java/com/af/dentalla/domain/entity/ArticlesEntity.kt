package com.af.dentalla.domain.entity


data class ArticlesEntity(
    val articleId: Int,
    val content: String,
    val postingTime: String,
    val doctorName: String,
    val articleImage: String?,
    val numberOfComments: Int,
    val title: String
)