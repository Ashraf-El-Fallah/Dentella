package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsItemDto
import com.af.dentalla.data.remote.requests.LoginPatient
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.data.remote.requests.SignUpUser
import kotlinx.coroutines.flow.Flow

interface PatientRepository {
    fun loginPatient(loginPatient: LoginPatient): Flow<NetWorkResponseState<Unit>>
    fun signUpPatient(signUpPatient: SignUpUser): Flow<NetWorkResponseState<Unit>>

    fun getAllDoctorsCards(): Flow<NetWorkResponseState<List<CardsItemDto>>>

}