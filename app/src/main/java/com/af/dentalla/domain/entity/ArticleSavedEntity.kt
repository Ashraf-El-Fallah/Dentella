package com.af.dentalla.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_saved_articles")
data class ArticleSavedEntity(
    @PrimaryKey(autoGenerate = true)
    val articleId: Int,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "posting_time")
    val postingTime: String,
    @ColumnInfo(name = "doctor_name")
    val doctorName: String,
    @ColumnInfo(name = "doctor_image")
    val doctorImage: String?,
    @ColumnInfo(name = "article_image")
    val articleImage: String?,
    @ColumnInfo(name = "title")
    val title: String
)
