package com.af.dentalla.data.source.local

import com.af.dentalla.data.local.database.ArticlesDao
import com.af.dentalla.domain.entity.ArticleSavedEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val articlesDao: ArticlesDao
) : LocalDataSource {
    override suspend fun saveArticle(articleSavedEntity: ArticleSavedEntity) {
        return articlesDao.insertArticle(articleSavedEntity)
    }

    override fun getAllSavedArticles(): List<ArticleSavedEntity> {
        return articlesDao.getAllArticles()
    }

    override suspend fun deleteArticle(articleSavedEntity: ArticleSavedEntity) {
        return articlesDao.deleteSavedArticle(articleSavedEntity)
    }
}