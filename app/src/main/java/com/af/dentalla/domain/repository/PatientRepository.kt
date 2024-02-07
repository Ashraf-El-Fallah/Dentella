package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.LoginResponse
import com.af.dentalla.data.requests.LoginUser
import com.af.dentalla.data.requests.SignUpPatient
import com.af.dentalla.domain.entity.LoginEntity
import com.af.dentalla.domain.entity.SignUpEntity
import kotlinx.coroutines.flow.Flow

interface PatientRepository {
    fun loginPatient(loginUser: LoginUser): Flow<NetWorkResponseState<LoginEntity>>

    fun signUpPatient(signUpPatient: SignUpPatient): Flow<NetWorkResponseState<SignUpEntity>>
}