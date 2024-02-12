package com.af.dentalla.data.remote.api


import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsItemDto
import com.af.dentalla.data.remote.dto.LoginResponse
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.SignUpUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

//    @POST("/api/Account/patient/register")
//    suspend fun signUpPatient(@Body signUpPatient: SignUpPatient): SignUpResponse//Response<SignUpResponse>


    @POST("/api/Account/{user_type}/register")
    suspend fun signUpUser(
        @Path("user_type") userType: String,
        @Body user: SignUpUser
    ): Response<Unit>

    @POST("/api/Account/{user_type}/login")
    suspend fun loginUser(
        @Path("user_type") userType: String,
        @Body user: LoginUser
    ): Response<LoginResponse>

    @GET("/api/Card/GetAllCards")
    suspend fun getAllDoctorsCards(): Response<List<CardsItemDto>>//Response<Cards>

    @GET("/api/Article/GetAllArticles")
    suspend fun getAllArticles(): Response<List<ArticleDto>>
}