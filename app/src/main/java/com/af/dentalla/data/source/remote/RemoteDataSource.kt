package com.af.dentalla.data.source.remote

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.data.remote.dto.PostDtoItem
import com.af.dentalla.data.remote.dto.UserProfileInformationDto
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.data.remote.requests.Card
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.Post
import com.af.dentalla.data.remote.requests.SignUpUser
import com.af.dentalla.data.remote.requests.UserPasswords
import com.af.dentalla.data.remote.requests.UserProfileInformation
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun loginUser(loginUser: LoginUser): Flow<NetWorkResponseState<Unit>>
    fun signUpUser(signUpUser: SignUpUser): Flow<NetWorkResponseState<Unit>>

    fun getAllDoctorsCards(): Flow<NetWorkResponseState<List<CardsDto>>>

    fun getAllArticles(): Flow<NetWorkResponseState<List<ArticleDto>>>

    fun getCardsBySearchByUniversity(university: String): Flow<NetWorkResponseState<List<CardsDto>>>

    fun getDoctorProfileDetails(cardId: Int): Flow<NetWorkResponseState<DoctorProfileDto>>

    fun getSpecialityDoctorsCards(specialityId: Int): Flow<NetWorkResponseState<List<CardsDto>>>

    fun getAllPosts(): Flow<NetWorkResponseState<List<PostDtoItem>>>

    fun addArticle(article: Article): Flow<NetWorkResponseState<Unit>>
    fun addPost(post: Post): Flow<NetWorkResponseState<Unit>>

    fun addCard(card: Card): Flow<NetWorkResponseState<Unit>>

    fun returnUserProfileInformation(): Flow<NetWorkResponseState<UserProfileInformationDto>>

    fun updateUserProfileInformation(userProfileInformation: UserProfileInformation): Flow<NetWorkResponseState<Unit>>

    fun changeUserPassword(userPasswords: UserPasswords): Flow<NetWorkResponseState<Unit>>

    fun logout(): Flow<NetWorkResponseState<Unit>>
    fun deleteUserInfo(): Flow<NetWorkResponseState<Unit>>
}