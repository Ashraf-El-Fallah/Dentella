package com.af.dentalla.data.repository

import android.util.Log
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.api.ApiService
import com.af.dentalla.data.remote.requests.LoginDoctor
import com.af.dentalla.data.remote.requests.SignUpUser
import com.af.dentalla.domain.repository.DoctorRepository
import com.af.dentalla.utilities.AccountManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val commonRepositoryImpl: CommonRepositoryImpl,
) : DoctorRepository {
    private val accountType = AccountManager.accountType
    override fun loginDoctor(loginDoctor: LoginDoctor): Flow<NetWorkResponseState<Unit>> =
        flow {
            Log.d("LoginDoctor", "Start login Doctor $loginDoctor")
            emit(NetWorkResponseState.Loading)
            try {
                val validateLoginResponse =
                    service.loginUser(accountType.toString().lowercase(), loginDoctor)
                if (validateLoginResponse.isSuccessful) {
                    commonRepositoryImpl.saveToken(validateLoginResponse.body()?.token)
                    Log.d("LoginDoctor", "Login Successfully")
                    emit(NetWorkResponseState.Success(Unit))
                } else {
                    val errorMessage =
                        validateLoginResponse.errorBody()?.toString() ?: "Unknown Error"
                    Log.d("LoginDoctor", "Login failed :$errorMessage")
                    emit(NetWorkResponseState.Error(Exception("Http error ${validateLoginResponse.code()}:$errorMessage")))
                }
            } catch (e: Exception) {
                Log.d("LoginDoctor", "Exception during login  ${e.message}", e)
                emit(NetWorkResponseState.Error(Exception("Exception :${e.message}")))
            }
        }

    override fun signUpDoctor(signUpDoctor: SignUpUser): Flow<NetWorkResponseState<Unit>> = flow {
        Log.d("SignUpPatient", "Start signing up patient $signUpDoctor")
        emit(NetWorkResponseState.Loading)
        try {
            val validateSignUpResponse =
                service.signUpUser(accountType.toString().lowercase(), signUpDoctor)
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
}

//    override fun signUpDoctor(
//        signUpDoctor: SignUpDoctor
//    ): Flow<NetWorkResponseState<Unit>> = flow {
//        Log.d("SignUpPatient", "Start signing up patient $signUpDoctor")
//        emit(NetWorkResponseState.Loading)
//        try {
//            val validateSignUpResponse =
//                service.signUpUser(accountType.toString().lowercase(), signUpDoctor)
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