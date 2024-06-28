package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.data.remote.requests.Card
import com.af.dentalla.data.remote.requests.UserPasswords
import com.af.dentalla.data.remote.requests.UserProfileInformation
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.Post
import com.af.dentalla.data.remote.requests.SignUpUser
import com.af.dentalla.domain.entity.ArticleSavedEntity
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.entity.DoctorProfileEntity
import com.af.dentalla.domain.entity.PostEntity
import com.af.dentalla.domain.entity.ProfileInformationEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun loginUser(loginUser: LoginUser): Flow<NetWorkResponseState<Unit>>
    fun signUpUser(signUpUser: SignUpUser): Flow<NetWorkResponseState<Unit>>

    fun getAllDoctorsCards(): Flow<NetWorkResponseState<List<CardsEntity>>>

    fun getAllArticles(): Flow<NetWorkResponseState<List<ArticlesEntity>>>

    fun getCardsBySearchByUniversity(university: String): Flow<NetWorkResponseState<List<CardsEntity>>>

    fun getDoctorProfileDetails(cardId: Int): Flow<NetWorkResponseState<DoctorProfileEntity>>

    fun getSpecialityDoctorsCards(specialityId: Int): Flow<NetWorkResponseState<List<CardsEntity>>>

    fun getAllPosts(): Flow<NetWorkResponseState<List<PostEntity>>>

    fun addArticle(article: Article): Flow<NetWorkResponseState<Unit>>
    fun addPost(post: Post): Flow<NetWorkResponseState<Unit>>

    fun addCard(card: Card): Flow<NetWorkResponseState<Unit>>

    fun returnUserProfileInformation(): Flow<NetWorkResponseState<ProfileInformationEntity>>

    fun updateUserProfileInformation(userProfileInformation: UserProfileInformation): Flow<NetWorkResponseState<Unit>>

    fun changeUserPassword(userPasswords: UserPasswords): Flow<NetWorkResponseState<Unit>>

    fun logout(): Flow<NetWorkResponseState<Unit>>
    fun deleteUserInfo(): Flow<NetWorkResponseState<Unit>>

    fun getSavedArticlesFromDataBase(): Flow<NetWorkResponseState<List<ArticleSavedEntity>>>

    suspend fun insertArticleToDataBase(articleSavedEntity: ArticlesEntity)

    suspend fun deleteSavedArticle(articleSavedEntity: ArticleSavedEntity)
}