package com.af.dentalla.di.mapper

import com.af.dentalla.data.mapper.ArticlesEntityMapper
import com.af.dentalla.data.mapper.CardsEntityMapper
import com.af.dentalla.data.mapper.DoctorProfileEntityMapper
import com.af.dentalla.data.remote.dto.LoginResponse
import com.af.dentalla.domain.mapper.BaseMapper
import com.af.dentalla.data.mapper.LoginEntityMapper
import com.af.dentalla.data.mapper.PostsEntityMapper
import com.af.dentalla.data.mapper.ProfileInformationMapper
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.data.remote.dto.PostDtoItem
import com.af.dentalla.data.remote.dto.UserProfileInformationDto
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.entity.DoctorProfileEntity
import com.af.dentalla.domain.entity.LoginEntity
import com.af.dentalla.domain.entity.PostEntity
import com.af.dentalla.domain.entity.ProfileInformationEntity
import com.af.dentalla.domain.mapper.ListMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MapperModule {
    @Binds
    @ViewModelScoped
    abstract fun bindLoginEntityMapper(loginEntityMapper: LoginEntityMapper): BaseMapper<LoginResponse, LoginEntity>

    @Binds
    @ViewModelScoped
    abstract fun bindDoctorEntityMapper(doctorProfileEntityMapper: DoctorProfileEntityMapper): BaseMapper<DoctorProfileDto, DoctorProfileEntity>

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

    @Binds
    @ViewModelScoped
    abstract fun bindProfileInformationMapper(profileInformationMapper: ProfileInformationMapper): BaseMapper<UserProfileInformationDto, ProfileInformationEntity>
}