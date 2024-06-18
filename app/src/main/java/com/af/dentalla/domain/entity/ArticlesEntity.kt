package com.af.dentalla.domain.entity


data class ArticlesEntity(
    val articleId: Int,
    val content: String,
    val postingTime: String,
    val doctorName: String,
    val doctorImage: String?,
    val articleImage: String?,
    val title: String
)