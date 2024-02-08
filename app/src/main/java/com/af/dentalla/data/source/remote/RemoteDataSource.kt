package com.af.dentalla.data.source.remote

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.LoginResponse
import com.af.dentalla.data.remote.dto.SignUpResponse
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.SignUpDoctor
import com.af.dentalla.data.remote.requests.SignUpPatient
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteDataSource {
//    fun loginDoctor(user: LoginUser): Flow<NetWorkResponseState<LoginResponse>>
//
//    fun signUpPatient(signUpPatient: SignUpPatient): Flow<NetWorkResponseState<SignUpResponse>>
//
//    fun signUpDoctor(signUpDoctor: SignUpDoctor): Flow<NetWorkResponseState<SignUpResponse>>
//
//    fun loginPatient(user: LoginUser): Flow<NetWorkResponseState<LoginResponse>>
}