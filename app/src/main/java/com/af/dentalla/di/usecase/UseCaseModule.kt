package com.af.dentalla.di.usecase


import com.af.dentalla.data.mapper.CardsEntityMapper
import com.af.dentalla.data.mapper.ArticlesEntityMapper
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.mapper.ListMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

//    @Binds
//    @ViewModelScoped
//    abstract fun bindLoginInUseCase(
//        loginUseCaseImpl: LoginPatientUseCaseImpl
//    ): LoginPatientUseCase

//    @Binds
//    @ViewModelScoped
//    abstract fun bindLoginInDoctorUseCase(
//        loginUseCaseImpl: LoginDoctorUseCaseImpl
//    ): LoginDoctorUseCase


//    @Binds
//    @ViewModelScoped
//    abstract fun bindSignUpPatientUseCase(
//        signUpUseCaseImpl: SignUpPatientUseCase
//    ): SignUpPatientUseCase

//    @Binds
//    @ViewModelScoped
//    abstract fun bindSignUpDoctorUseCase(
//        signUpUseCaseImpl: SignUpDoctorUseCaseImpl
//    ): SignUpDoctorUseCase

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
}