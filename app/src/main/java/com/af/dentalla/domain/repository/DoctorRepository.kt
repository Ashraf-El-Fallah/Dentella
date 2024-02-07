package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.requests.LoginUser
import com.af.dentalla.data.requests.SignUpDoctor
import com.af.dentalla.data.requests.SignUpPatient
import com.af.dentalla.domain.entity.LoginEntity
import com.af.dentalla.domain.entity.SignUpEntity
import kotlinx.coroutines.flow.Flow

interface DoctorRepository {

    fun loginDoctor(loginUser: LoginUser): Flow<NetWorkResponseState<LoginEntity>>

    fun signUpDoctor(signUpDoctor: SignUpDoctor): Flow<NetWorkResponseState<SignUpEntity>>

}