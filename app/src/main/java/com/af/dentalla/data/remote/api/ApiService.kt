package com.af.dentalla.data.remote.api


import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.data.remote.dto.LoginResponse
import com.af.dentalla.data.remote.dto.PostDtoItem
import com.af.dentalla.data.remote.dto.UserProfileInformationDto
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.data.remote.requests.Card
import com.af.dentalla.data.remote.requests.DoctorPassword
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.Post
import com.af.dentalla.data.remote.requests.SignUpUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @GET("{user_type}/ReturnProfile")
    suspend fun returnUserProfileInformation(@Path("user_type") userType: String): UserProfileInformationDto

    //    @PUT("Doctor/UpdateProfile")
//    suspend fun updateDoctorProfile(
//        @Body doctorProfileInformation: DoctorProfileInformation
//    )
    @Multipart
    @PUT("{user_type}/UpdateProfile")
    suspend fun updateUserProfileInformation(
        @Path("user_type") userType: String,
        @Part("userName") userName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part("currentLevel") currentLevel: RequestBody,
        @Part("currentUniversity") currentUniversity: RequestBody,
        @Part photo: MultipartBody.Part?
    )

//    data class UserProfileUpdateRequest(
//        val userName: String,
//        val email: String,
//        val phoneNumber: String,
//        val bio: String,
//        val currentLevel: String,
//        val currentUniversity: String,
//        val photo: MultipartBody.Part?
//    )
//
//    @PUT("{user_type}/UpdateProfile")
//    suspend fun updateUserProfileInformation(
//        @Path("user_type") userType: String,
//        @Body userProfileUpdateRequest: UserProfileUpdateRequest
//    )

    @POST("Password/changepassword")
    suspend fun changeDoctorPassword(
        @Body doctorPassword: DoctorPassword
    )

    @POST("Account/logout")
    suspend fun logoutFromAccount()
}