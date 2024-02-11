package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsItemDto
import kotlinx.coroutines.flow.Flow

interface PatientRepository {
    suspend fun loginPatient(userName: String, password: String): Boolean
    suspend fun signUpPatient(
        userName: String,
        email: String,
        phone: String,
        password: String
    ): Boolean

    fun getAllDoctorsCards(): Flow<NetWorkResponseState<List<CardsItemDto>>>

}