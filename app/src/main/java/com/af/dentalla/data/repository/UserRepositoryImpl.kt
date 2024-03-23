package com.af.dentalla.data.repository

import android.util.Log
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.data.remote.api.ApiService
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.data.remote.dto.PostDtoItem
import com.af.dentalla.data.remote.requests.AddArticle
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.data.remote.requests.SignUpUser
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.utilities.AccountManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val dataStorePreferencesService: DataStorePreferencesService
) : UserRepository {
    private val accountType = AccountManager.accountType

    override fun loginUser(loginUser: LoginUser): Flow<NetWorkResponseState<Unit>> {
        return flow {
            Log.d("LoginUser", "Start login user $loginUser")
            try {
                emit(NetWorkResponseState.Loading)
                val authenticateLoginResponse =
                    service.loginUser(accountType.toString().lowercase(), loginUser)
                emit(NetWorkResponseState.Success(Unit))
                saveToken(authenticateLoginResponse.token)

//                if (authenticateLoginResponse.isSuccessful) {
//                    saveToken(authenticateLoginResponse.body()?.token)
//                    Log.d("LoginUser", "Login Successfully")
//                    emit(NetWorkResponseState.Success(Unit))
//                } else {
//                    val errorMessage =
//                        authenticateLoginResponse.errorBody()?.toString() ?: "Unknown Error"
//                    Log.d("LoginUser", "Login failed :$errorMessage")
//                    emit(NetWorkResponseState.Error(Exception("Http error ${authenticateLoginResponse.code()}:$errorMessage")))
//                }
            } catch (e: Exception) {
                Log.d("LoginUser", "Exception during login  ${e.message}", e)
                emit(NetWorkResponseState.Error(Exception("Exception :${e.message}")))
            }
        }
    }

    //
    override fun signUpUser(signUpUser: SignUpUser): Flow<NetWorkResponseState<Unit>> {
        return flow {
            Log.d("SignUpUser", "Start signing up user $signUpUser")
            emit(NetWorkResponseState.Loading)
            try {
                val authenticateSignUpResponse =
                    service.signUpUser(accountType.toString().lowercase(), signUpUser)
                emit(NetWorkResponseState.Success(Unit))

                //if (authenticateSignUpResponse.isSuccessful)
//                {
//                    Log.d("SignUpUser", "Sign Up Successfully")
//                    emit(NetWorkResponseState.Success(Unit))
//                } else {
//                    val errorMessage =
//                        authenticateSignUpResponse.errorBody()?.toString() ?: "Unknown Error"
//                    Log.d("SignUpUser", "Sign Up failed :$errorMessage")
//                    emit(NetWorkResponseState.Error(Exception("Http error ${authenticateSignUpResponse.code()}:$errorMessage")))
//                }
                Log.d("SignUpUser", "............ $signUpUser")
            } catch (e: Exception) {
                Log.d("SignUpUser", "Exception during sign up  ${e.message}")
                emit(NetWorkResponseState.Error(Exception("Exception: ${e.message}")))
            }
        }
    }

    override fun getAllDoctorsCards(): Flow<NetWorkResponseState<List<CardsDto>>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)

                val response = service.getAllDoctorsCards()
                emit(NetWorkResponseState.Success(response))
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    if (data != null) {
//                        emit(NetWorkResponseState.Success(data))
//                    } else {
//                        emit(NetWorkResponseState.Error(Exception("Response body is null")))
//                    }
//                } else {
//                    emit(NetWorkResponseState.Error(Exception("HTTP error ${response.code()}")))
//                }
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }


    override fun getAllArticles(): Flow<NetWorkResponseState<List<ArticleDto>>> {
        return flow {

            try {
                emit(NetWorkResponseState.Loading)
                Log.d("Articles", "Start getting articles")
                val response = service.getAllArticles()
                emit(NetWorkResponseState.Success(response))
                Log.d("Articles", "Articles are getting successfully")

//                if (response.isSuccessful) {
//                    Log.d("Articles", "Articles got successfully")
//                    val data = response.body()
//                    if (data != null) {
//                        Log.d("Articles", "Articles got successfully")
//                        emit(NetWorkResponseState.Success(data))
//                    } else {
//                        Log.d("Articles", "Body is null")
//                        emit(NetWorkResponseState.Error(Exception("Response body is null")))
//                    }
//                } else {
//                    Log.d("Articles", "HTTP error")
//                    emit(NetWorkResponseState.Error(Exception("Http error ${response.body()}")))
//                }
            } catch (e: Exception) {
                Log.d("Articles", "exception")
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun getCardsBySearchByUniversity(university: String): Flow<NetWorkResponseState<List<CardsDto>>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                val response = service.searchAboutDoctorsByUniversity(university)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        emit(NetWorkResponseState.Success(data))
                    } else {
                        emit(NetWorkResponseState.Error(Exception("Response body is null")))
                    }
                } else {
                    emit(NetWorkResponseState.Error(Exception("HTTp error ${response.body()}")))
                }
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun getDoctorProfileDetails(cardId: Int): Flow<NetWorkResponseState<DoctorProfileDto>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)
                val response = service.getDoctorProfile(cardId)
                Log.d("ProfileDetails", response.doctorName.toString())
                emit(NetWorkResponseState.Success(response))

//                if (response.isSuccessful) {
//                    val data = response.body()
//                    if (data != null) {
//                        emit(NetWorkResponseState.Success(data))
//                    } else {
//                        emit(NetWorkResponseState.Error(Exception("Response body is null")))
//                    }
//                } else {
//                    emit(NetWorkResponseState.Error(Exception("HTTp error ${response.body()}")))
//                }
            } catch (e: Exception) {
                Log.d("ProfileDetails", e.message.toString())
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun getSpecialityDoctorsCards(specialityId: Int): Flow<NetWorkResponseState<List<CardsDto>>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)
                val response = service.getSpecialityCards(specialityId)
                emit(NetWorkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun getAllPosts(): Flow<NetWorkResponseState<List<PostDtoItem>>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)
                val response = service.getAllPosts()
                emit(NetWorkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun addArticle(article: AddArticle): Flow<NetWorkResponseState<Unit>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                emit(NetWorkResponseState.Success(Unit))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }


    private suspend fun saveToken(token: String?) {
        dataStorePreferencesService.saveToken(token)
    }


}

