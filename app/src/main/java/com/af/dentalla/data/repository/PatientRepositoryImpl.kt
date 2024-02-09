package com.af.dentalla.data.repository

import com.af.dentalla.data.DataClassParser
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.data.remote.api.ApiService
import com.af.dentalla.data.remote.dto.SignUpResponse
import com.af.dentalla.data.mapper.BaseMapper
import com.af.dentalla.data.remote.dto.LoginErrorResponse
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.di.coroutine.IoDispatcher
import com.af.dentalla.domain.entity.SignUpEntity
import com.af.dentalla.domain.repository.PatientRepository
import com.af.dentalla.utilities.AccountManager
import com.af.dentalla.utilities.mapResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val signUpEntityMapper: BaseMapper<SignUpResponse, SignUpEntity>,
    private val dataStorePreferencesService: DataStorePreferencesService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dataClassParser: DataClassParser
) : PatientRepository {
    override suspend fun loginPatient(userName: String, password: String): Boolean {
        try {
//            val token = dataStorePreferencesService.getToken()
            val body = mapOf<String, Any>(
                "userName" to userName,
                "passWord" to password,
//                "token" to token
            ).toMap()

            val accountType = AccountManager.accountType

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

    override fun signUpPatient(signUpPatient: SignUpPatient): Boolean {
        TODO("Not yet implemented")
    }

    private suspend fun saveToken(token: String?, expireDate: String) {
        dataStorePreferencesService.saveTokenAndExpireDate(token, expireDate)
    }


//    override fun signUpPatient(signUpPatient: SignUpPatient): Flow<NetWorkResponseState<SignUpEntity>> =
//        remoteDataSource.signUpPatient(signUpPatient).map {
//            it.mapResponse {
//                signUpEntityMapper.map(this)
//            }
//        }.flowOn(ioDispatcher)

}

//        remoteDataSource.loginPatient(loginUser).map {
//            it.mapResponse {
//                loginEntityMapper.map(this)
//            }
//        }.flowOn(ioDispatcher)
