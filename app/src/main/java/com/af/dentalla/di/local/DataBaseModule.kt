package com.af.dentalla.di.local

import android.content.Context
import androidx.room.Room
import com.af.dentalla.data.local.database.ArticleDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ArticleDataBase {
        return Room.databaseBuilder(
            context,
            ArticleDataBase::class.java,
            "user_saved_articles",
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideCartDao(articleDataBase: ArticleDataBase) = articleDataBase.articlesDao()
}
