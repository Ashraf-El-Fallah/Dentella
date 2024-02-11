package com.af.dentalla.data.repository

import com.af.dentalla.data.DataClassParser
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.api.ApiService
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.LoginErrorResponse
import com.af.dentalla.data.remote.dto.LoginResponse
import com.af.dentalla.data.remote.dto.SignUpErrorResponse
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.SignUpDoctor
import com.af.dentalla.domain.entity.LoginEntity
import com.af.dentalla.domain.entity.SignUpEntity
import com.af.dentalla.domain.repository.BaseRepository
import com.af.dentalla.domain.repository.DoctorRepository
import com.af.dentalla.utilities.AccountManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val baseRepositoryImpl: BaseRepositoryImpl,
    private val dataClassParser: DataClassParser
) : DoctorRepository {
    private val accountType = AccountManager.accountType
    override suspend fun loginDoctor(email: String, password: String): Boolean {
        try {
            val body = mapOf<String, Any>(
                "email" to email,
                "passWord" to password
            ).toMap()

            val validateLoginResponse = service.loginUser(accountType.toString().lowercase(), body)
            if (validateLoginResponse.isSuccessful) {
                validateLoginResponse.body()?.apply {
                    baseRepositoryImpl.saveToken(token, tokenExpiration)
                    return true
                }
            } else {
                val errorResponse = dataClassParser.parseFromJson(
                    validateLoginResponse.errorBody()?.toString(), LoginErrorResponse::class.java
                )
                throw Throwable("This use is ${errorResponse.error}")
            }
        } catch (e: Exception) {
            throw Throwable(e)
        }
        return false
    }

    override suspend fun signUpDoctor(
        userName: String,
        email: String,
        phone: String,
        password: String,
        id: String
    ): Boolean {
        try {
            val body = mapOf<String, Any>(
                "email" to email,
                "username" to userName,
                "password" to password,
                "phone_number" to phone,
                "dentistID" to id
            ).toMap()
            val validateSignUpResponse =
                service.signUpUser(accountType.toString().lowercase(), body)
            if (validateSignUpResponse.isSuccessful) {
                validateSignUpResponse.body()?.apply {
                    NetWorkResponseState.Success(Unit)
                }
                return true
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
}

//    override fun loginDoctor(loginUser: LoginUser): Flow<NetWorkResponseState<LoginEntity>> =
//        remoteDataSource.loginDoctor(loginUser).map {
//            it.mapResponse {
//                loginEntityMapper.map(this)
//            }
//        }.flowOn(ioDispatcher)
//
//    override fun signUpDoctor(signUpDoctor: SignUpDoctor): Flow<NetWorkResponseState<SignUpEntity>> =
//        remoteDataSource.signUpDoctor(signUpDoctor).map {
//            it.mapResponse {
//                signUpEntityMapper.map(this)
//            }
//        }.flowOn(ioDispatcher)