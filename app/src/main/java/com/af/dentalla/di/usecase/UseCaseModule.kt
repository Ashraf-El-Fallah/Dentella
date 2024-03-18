package com.af.dentalla.di.usecase


import com.af.dentalla.data.mapper.CardsEntityMapper
import com.af.dentalla.data.mapper.ArticlesEntityMapper
import com.af.dentalla.data.mapper.PostsEntityMapper
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.data.remote.dto.PostDtoItem
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.entity.PostEntity
import com.af.dentalla.domain.mapper.ListMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    @ViewModelScoped
    abstract fun bindsAllDoctorsCards(
        cardsEntityMapper: CardsEntityMapper
    ): ListMapper<CardsDto, CardsEntity>

    @Binds
    @ViewModelScoped
    abstract fun bindsAllArticles(
        allArticlesEntityMapper: ArticlesEntityMapper
    ): ListMapper<ArticleDto, ArticlesEntity>

    @Binds
    @ViewModelScoped
    abstract fun bindsAllPosts(
        allPostsEntityMapper: PostsEntityMapper
    ): ListMapper<PostDtoItem, PostEntity>
}