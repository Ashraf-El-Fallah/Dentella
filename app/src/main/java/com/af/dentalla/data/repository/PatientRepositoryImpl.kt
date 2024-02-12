package com.af.dentalla.data.repository

import android.util.Log
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.api.ApiService
import com.af.dentalla.data.remote.dto.CardsItemDto
import com.af.dentalla.data.remote.requests.LoginPatient
import com.af.dentalla.data.remote.requests.SignUpUser
import com.af.dentalla.domain.repository.PatientRepository
import com.af.dentalla.utilities.AccountManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val commonRepositoryImpl: CommonRepositoryImpl
) : PatientRepository {
    private val accountType = AccountManager.accountType

    override fun loginPatient(loginPatient: LoginPatient): Flow<NetWorkResponseState<Unit>> =
        flow {
            Log.d("LoginPatient", "Start login patient $loginPatient")
            emit(NetWorkResponseState.Loading)
            try {
                val validateLoginResponse =
                    service.loginUser(accountType.toString().lowercase(), loginPatient)
                if (validateLoginResponse.isSuccessful) {
                    commonRepositoryImpl.saveToken(validateLoginResponse.body()?.token)
                    Log.d("LoginPatient", "Login Successfully")
                    emit(NetWorkResponseState.Success(Unit))
                } else {
                    val errorMessage =
                        validateLoginResponse.errorBody()?.toString() ?: "Unknown Error"
                    Log.d("LoginPatient", "Login failed :$errorMessage")
                    emit(NetWorkResponseState.Error(Exception("Http error ${validateLoginResponse.code()}:$errorMessage")))
                }
            } catch (e: Exception) {
                Log.d("LoginPatient", "Exception during login  ${e.message}", e)
                emit(NetWorkResponseState.Error(Exception("Exception :${e.message}")))
            }
        }

    override fun signUpPatient(signUpPatient: SignUpUser): Flow<NetWorkResponseState<Unit>> = flow {
        Log.d("SignUpPatient", "Start signing up patient $signUpPatient")
        emit(NetWorkResponseState.Loading)
        try {
            val validateSignUpResponse =
                service.signUpUser(accountType.toString().lowercase(), signUpPatient)
            if (validateSignUpResponse.isSuccessful) {
                Log.d("SignUpPatient", "Sign Up Successfully")
                emit(NetWorkResponseState.Success(Unit))
            } else {
                val errorMessage = validateSignUpResponse.errorBody()?.toString() ?: "Unknown Error"
                Log.d("SignUpPatient", "Sign Up failed :$errorMessage")
                emit(NetWorkResponseState.Error(Exception("Http error ${validateSignUpResponse.code()}:$errorMessage")))
            }
        } catch (e: Exception) {
            Log.d("SignUpPatient", "Exception during sign up  ${e.message}")
            emit(NetWorkResponseState.Error(Exception("Exception: ${e.message}")))
        }
    }


//    override fun signUpPatient(
//        signUpPatient: SignUpPatient
//    ): Flow<NetWorkResponseState<Unit>> = flow {
//        Log.d("SignUpPatient", "Start signing up patient $signUpPatient")
//        emit(NetWorkResponseState.Loading)
//        try {
//            val validateSignUpResponse =
//                service.signUpUser(accountType.toString().lowercase(), signUpPatient)
//            if (validateSignUpResponse.isSuccessful) {
//                Log.d("SignUpPatient", "Sign Up Successfully")
//                emit(NetWorkResponseState.Success(Unit))
//            } else {
//                val errorMessage = validateSignUpResponse.errorBody()?.toString() ?: "Unknown Error"
//                Log.d("SignUpPatient", "Sign Up failed :$errorMessage")
//                emit(NetWorkResponseState.Error(Exception("Http error ${validateSignUpResponse.code()}:$errorMessage")))
//            }
//        } catch (e: Exception) {
//            Log.d("SignUpPatient", "Exception during sign up  ${e.message}")
//            emit(NetWorkResponseState.Error(Exception("Exception: ${e.message}")))
//        }
//    }

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