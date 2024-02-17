package com.af.dentalla.data.remote.api


import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.data.remote.dto.LoginResponse
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.data.remote.requests.SignUpUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //    @Headers("Authorization: Basic MTExNjEwNzY6NjAtZGF5ZnJlZXRyaWFs", "Content-Type: application/json")
    @POST("Account/{user_type}/register")
    suspend fun signUpUser(
        @Path("user_type") userType: String,
        @Body user: SignUpUser
    ): Unit

    //    @Headers("Authorization: Basic MTExNjEwNzY6NjAtZGF5ZnJlZXRyaWFs", "Content-Type: application/json")
    @POST("Account/{user_type}/login")
    suspend fun loginUser(
        @Path("user_type") userType: String,
        @Body user: LoginUser
    ): LoginResponse//Response<LoginResponse>

    @GET("Card/GetAllCards")
    suspend fun getAllDoctorsCards(): List<CardsDto>

    @GET("Article/GetAllArticles")
    suspend fun getAllArticles(): List<ArticleDto>

    @GET("Card/SearchCardsByUniversity/{university}")
    suspend fun searchAboutDoctorsByUniversity(
        @Path("university") university: String
    ): Response<List<CardsDto>>

    @GET("Card/GetCardDetails/{cardId}")
    suspend fun getDoctorProfile(
        @Path("cardId") cardId: Int
    ): DoctorProfileDto

}