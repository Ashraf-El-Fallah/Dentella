package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.LoginResponse
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.domain.entity.LoginEntity
import com.af.dentalla.domain.entity.SignUpEntity
import kotlinx.coroutines.flow.Flow

interface PatientRepository {
    suspend fun loginPatient(userName: String, password: String): Boolean

    fun signUpPatient(signUpPatient: SignUpPatient): Boolean
}