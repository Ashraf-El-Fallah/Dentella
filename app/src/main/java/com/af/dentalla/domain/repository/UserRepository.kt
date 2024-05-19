package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.data.remote.dto.PostDtoItem
import com.af.dentalla.data.remote.dto.ProfileInformationDto
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.data.remote.requests.Card
import com.af.dentalla.data.remote.requests.DoctorPassword
import com.af.dentalla.data.remote.requests.DoctorProfileInformation
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.Post
import com.af.dentalla.data.remote.requests.SignUpUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
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

    fun returnDoctorProfileInformation(): Flow<NetWorkResponseState<ProfileInformationDto>>

    fun updateDoctorProfileInformation(doctorProfileInformation: DoctorProfileInformation): Flow<NetWorkResponseState<Unit>>

    fun changeDoctorPassword(doctorPassword: DoctorPassword): Flow<NetWorkResponseState<Unit>>

    fun logout(): Flow<NetWorkResponseState<Unit>>
}