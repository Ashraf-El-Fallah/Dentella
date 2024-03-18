package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.data.remote.dto.PostDtoItem
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.data.remote.requests.SignUpUser
import com.af.dentalla.ui.patient.homeScreen.Speciality
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
}