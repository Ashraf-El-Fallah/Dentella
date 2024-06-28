package com.af.dentalla.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.af.dentalla.domain.entity.ArticleSavedEntity

@Database(entities = [ArticleSavedEntity::class], version = 1)
abstract class ArticleDataBase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}