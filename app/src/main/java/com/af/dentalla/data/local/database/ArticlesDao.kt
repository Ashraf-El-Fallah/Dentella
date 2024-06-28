package com.af.dentalla.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af.dentalla.domain.entity.ArticleSavedEntity

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articleSavedEntity: ArticleSavedEntity)

    @Query("SELECT * FROM user_saved_articles")
    fun getAllArticles(): List<ArticleSavedEntity>

    @Delete
    suspend fun deleteSavedArticle(articleSavedEntity: ArticleSavedEntity)
}