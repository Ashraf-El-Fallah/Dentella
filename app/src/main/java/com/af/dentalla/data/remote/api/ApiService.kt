package com.af.dentalla.data.remote.api


import com.af.dentalla.data.remote.dto.LoginResponse
import com.af.dentalla.data.remote.dto.SignUpResponse
import com.af.dentalla.utilities.AccountManager
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

//    @POST("/api/Account/patient/register")
//    suspend fun signUpPatient(@Body signUpPatient: SignUpPatient): SignUpResponse//Response<SignUpResponse>


    @FormUrlEncoded
    @POST("/api/Account/{user_type}/register")
    suspend fun signUpUser(
        @Path("user_type") user: String,
        @FieldMap body: Map<String, Any>
    ): Response<SignUpResponse>

    @POST("/api/Account/{user_type}/login")
    suspend fun loginUser(
        @Path("user_type") user: String,
        @FieldMap body: Map<String, Any>
    ): Response<LoginResponse>

//    @POST("/api/Account/LoginPatient")
//    suspend fun loginPatient(@Body loginUser: LoginUser): LoginResponse//Response<LoginResponse>
}