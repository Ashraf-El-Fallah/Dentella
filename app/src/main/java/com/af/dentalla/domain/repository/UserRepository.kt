package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsItemDto
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.SignUpUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun loginUser(loginUser: LoginUser): Flow<NetWorkResponseState<Unit>>
    fun signUpUser(signUpUser: SignUpUser): Flow<NetWorkResponseState<Unit>>

    fun getAllDoctorsCards(): Flow<NetWorkResponseState<List<CardsItemDto>>>

    fun getAllArticles(): Flow<NetWorkResponseState<List<ArticleDto>>>
}