package com.af.dentalla.data.api

import com.af.dentalla.data.requests.LoginUser
import com.af.dentalla.data.requests.SignUpDoctor
import com.af.dentalla.data.requests.SignUpPatient
import com.af.dentalla.data.dto.LoginResponse
import com.af.dentalla.data.dto.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/Account/patient/register")
    suspend fun signUpPatient(@Body signUpPatient: SignUpPatient): SignUpResponse//Response<SignUpResponse>

    @POST("/api/Account/doctor/register")
    suspend fun signUpDoctor(@Body signUpDoctor: SignUpDoctor): SignUpResponse//Response<SignUpResponse>

    @POST("/api/Account/LoginDoctor")
    suspend fun loginDoctor(@Body loginUser: LoginUser): LoginResponse//Response<LoginResponse>

    @POST("/api/Account/LoginPatient")
    suspend fun loginPatient(@Body loginUser: LoginUser): LoginResponse//Response<LoginResponse>
}