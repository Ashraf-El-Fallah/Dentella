package com.af.dentalla.data.repository

import android.util.Log
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.data.remote.api.ApiService
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsItemDto
import com.af.dentalla.data.remote.requests.LoginUser
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

    override fun loginUser(loginUser: LoginUser): Flow<NetWorkResponseState<Unit>> =
        flow {
            Log.d("LoginUser", "Start login user $loginUser")
            emit(NetWorkResponseState.Loading)
            try {
                val validateLoginResponse =
                    service.loginUser(accountType.toString().lowercase(), loginUser)
                if (validateLoginResponse.isSuccessful) {
                    saveToken(validateLoginResponse.body()?.token)
                    Log.d("LoginUser", "Login Successfully")
                    emit(NetWorkResponseState.Success(Unit))
                } else {
                    val errorMessage =
                        validateLoginResponse.errorBody()?.toString() ?: "Unknown Error"
                    Log.d("LoginUser", "Login failed :$errorMessage")
                    emit(NetWorkResponseState.Error(Exception("Http error ${validateLoginResponse.code()}:$errorMessage")))
                }
            } catch (e: Exception) {
                Log.d("LoginUser", "Exception during login  ${e.message}", e)
                emit(NetWorkResponseState.Error(Exception("Exception :${e.message}")))
            }
        }

    override fun signUpUser(signUpUser: SignUpUser): Flow<NetWorkResponseState<Unit>> = flow {
        Log.d("signUpUser", "Start signing up user $signUpUser")
        emit(NetWorkResponseState.Loading)
        try {
            val validateSignUpResponse =
                service.signUpUser(accountType.toString().lowercase(), signUpUser)
            if (validateSignUpResponse.isSuccessful) {
                Log.d("signUpUser", "Sign Up Successfully")
                emit(NetWorkResponseState.Success(Unit))
            } else {
                val errorMessage = validateSignUpResponse.errorBody()?.toString() ?: "Unknown Error"
                Log.d("signUpUser", "Sign Up failed :$errorMessage")
                emit(NetWorkResponseState.Error(Exception("Http error ${validateSignUpResponse.code()}:$errorMessage")))
            }
        } catch (e: Exception) {
            Log.d("signUpUser", "Exception during sign up  ${e.message}")
            emit(NetWorkResponseState.Error(Exception("Exception: ${e.message}")))
        }
    }

    override fun getAllDoctorsCards(): Flow<NetWorkResponseState<List<CardsItemDto>>> = flow {
        emit(NetWorkResponseState.Loading)
        try {
            val response = service.getAllDoctorsCards()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(NetWorkResponseState.Success(data))
                } else {
                    emit(NetWorkResponseState.Error(Exception("Response body is null")))
                }
            } else {
                emit(NetWorkResponseState.Error(Exception("HTTP error ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(NetWorkResponseState.Error(e))
        }
    }

    override fun getAllArticles(): Flow<NetWorkResponseState<List<ArticleDto>>> = flow {
        emit(NetWorkResponseState.Loading)
        try {
            val response = service.getAllArticles()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(NetWorkResponseState.Success(data))
                } else {
                    emit(NetWorkResponseState.Error(Exception("Response body is null")))
                }
            } else {
                emit(NetWorkResponseState.Error(Exception("Http error ${response.body()}")))
            }
        } catch (e: Exception) {
            emit(NetWorkResponseState.Error(e))
        }
    }

    suspend fun saveToken(token: String?) {
        dataStorePreferencesService.saveToken(token)
    }
}

////signUp
//override suspend fun signUpPatient(
//        userName: String,
//        email: String,
//        phone: String,
//        password: String
//    ): Boolean {
//        try {
//            val body = mapOf<String, Any>(
//                "email" to email,
//                "username" to userName,
//                "password" to password,
//                "phone_number" to phone
//            ).toMap()
//            val validateSignUpResponse =
//                service.signUpUser(accountType.toString().lowercase(), body)
//            if (validateSignUpResponse.isSuccessful) {
//                validateSignUpResponse.body()?.apply {
//                    NetWorkResponseState.Success(Unit)
//                    //validateSignUpResponse.body()?.message?.let { SignUpResponse(it) }
////                    return true
//                }
//                return true
//            } else {
//                val errorResponse = dataClassParser.parseFromJson(
//                    validateSignUpResponse.errorBody()?.toString(), SignUpErrorResponse::class.java
//                )
//                throw Throwable(errorResponse.message)
//            }
//        } catch (e: Exception) {
//            throw Throwable(e)
//        }
//        return false
//    }


//    override fun signUpPatient(signUpPatient: SignUpPatient): Flow<NetWorkResponseState<SignUpEntity>> =
//        remoteDataSource.signUpPatient(signUpPatient).map {
//            it.mapResponse {
//                signUpEntityMapper.map(this)
//            }
//        }.flowOn(ioDispatcher)


//        remoteDataSource.loginPatient(loginUser).map {
//            it.mapResponse {
//                loginEntityMapper.map(this)
//            }
//        }.flowOn(ioDispatcher)


//    override fun getAllDoctorsCards(): Flow<NetWorkResponseState<List<CardsItemDto>>> {
//        return flow {
//            emit(NetWorkResponseState.Loading)
//            try {
//                val response = service.getAllDoctorsCards()
//                emit(NetWorkResponseState.Success(response))
//            } catch (e: Exception) {
//                emit(NetWorkResponseState.Error(e))
//            }
//        }