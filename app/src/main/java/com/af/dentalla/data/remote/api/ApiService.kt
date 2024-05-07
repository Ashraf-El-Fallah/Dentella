package com.af.dentalla.data.remote.api


import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.data.remote.dto.LoginResponse
import com.af.dentalla.data.remote.dto.PostDtoItem
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.data.remote.requests.Card
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.Post
import com.af.dentalla.data.remote.requests.SignUpUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("Account/{user_type}/register")
    suspend fun signUpUser(
        @Path("user_type") userType: String,
        @Body user: SignUpUser
    )

    @POST("Account/{user_type}/login")
    suspend fun loginUser(
        @Path("user_type") userType: String,
        @Body user: LoginUser
    ): Response<LoginResponse>

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

    @GET("Card/GetSpecsificCards/{specialty}")
    suspend fun getSpecialityCards(
        @Path("specialty") specialty: Int
    ): List<CardsDto>

    @GET("Post/GetAllPosts")
    suspend fun getAllPosts(): List<PostDtoItem>

    @POST("Article/AddArticles")
    suspend fun addArticle(
        @Body article: Article
    )

    @POST("Post/AddPost")
    suspend fun addPost(
        @Body post: Post
    )

    @POST("Card/AddCard")
    suspend fun addCard(
        @Body card: Card
    )
}