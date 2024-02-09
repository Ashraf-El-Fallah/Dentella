package com.af.dentalla.data.repository

import android.app.Service
import com.af.dentalla.data.DataClassParser
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.data.remote.api.ApiService
import com.af.dentalla.data.remote.dto.SignUpResponse
import com.af.dentalla.data.mapper.BaseMapper
import com.af.dentalla.data.remote.dto.LoginErrorResponse
import com.af.dentalla.data.remote.dto.SignUpErrorResponse
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.di.coroutine.IoDispatcher
import com.af.dentalla.domain.entity.SignUpEntity
import com.af.dentalla.domain.repository.PatientRepository
import com.af.dentalla.utilities.AccountManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val dataStorePreferencesService: DataStorePreferencesService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dataClassParser: DataClassParser,
) : PatientRepository {
    private val accountType = AccountManager.accountType
    override suspend fun loginPatient(userName: String, password: String): Boolean {
        try {
            val body = mapOf<String, Any>(
                "userName" to userName,
                "passWord" to password,
            ).toMap()


            val validateLoginResponse = service.loginUser(accountType.toString().lowercase(), body)
            if (validateLoginResponse.isSuccessful) {
                validateLoginResponse.body()?.apply {
                    saveToken(token, tokenExpiration)
                    return true
                }
            } else {
                val errorResponse = dataClassParser.parseFromJson(
                    //still i don't know json
                    validateLoginResponse.errorBody()?.toString(), LoginErrorResponse::class.java
                )
                throw Throwable(errorResponse.error)
            }
        } catch (e: Exception) {
            throw Throwable(e)
        }
        return false
    }


    override suspend fun signUpPatient(
        userName: String,
        email: String,
        phone: String,
        password: String
    ): Boolean {
        try {
            val body = mapOf<String, Any>(
                "email" to email,
                "username" to userName,
                "password" to password,
                "phone_number" to phone
            ).toMap()
            val validateSignUpResponse =
                service.signUpUser(accountType.toString().lowercase(), body)
            if (validateSignUpResponse.isSuccessful) {
                validateSignUpResponse.body()?.apply {
                    validateSignUpResponse.body()?.message?.let { SignUpResponse(it) }
                    return true
                }
            } else {
                val errorResponse = dataClassParser.parseFromJson(
                    validateSignUpResponse.errorBody()?.toString(), SignUpErrorResponse::class.java
                )
                throw Throwable(errorResponse.message)
            }
        } catch (e: Exception) {
            throw Throwable(e)
        }
        return false
    }

    private suspend fun saveToken(token: String?, expireDate: String) {
        dataStorePreferencesService.saveTokenAndExpireDate(token, expireDate)
    }
}

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
