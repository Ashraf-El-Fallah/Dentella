package com.af.dentalla.data.source.local

import com.af.dentalla.domain.entity.ArticleSavedEntity

interface LocalDataSource {
    suspend fun saveArticle(articleSavedEntity: ArticleSavedEntity)
    fun getAllSavedArticles(): List<ArticleSavedEntity>
    suspend fun deleteArticle(articleSavedEntity: ArticleSavedEntity)
}