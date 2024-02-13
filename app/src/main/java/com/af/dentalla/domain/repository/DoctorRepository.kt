package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.LoginDoctor
import com.af.dentalla.data.remote.requests.LoginPatient
import com.af.dentalla.data.remote.requests.SignUpDoctor
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.data.remote.requests.SignUpUser
import kotlinx.coroutines.flow.Flow

//interface DoctorRepository {
//
//    fun loginDoctor(loginDoctor: LoginDoctor): Flow<NetWorkResponseState<Unit>>
//    fun signUpDoctor(signUpDoctor: SignUpUser): Flow<NetWorkResponseState<Unit>>
//
//
//}