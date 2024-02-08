package com.af.dentalla.data.source.remote

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.api.ApiService
import com.af.dentalla.data.remote.dto.LoginResponse
import com.af.dentalla.data.remote.dto.SignUpResponse
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.SignUpDoctor
import com.af.dentalla.data.remote.requests.SignUpPatient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    ////////////////////////////////////////////////////////////////////////////////////////////////
//    override fun loginDoctor(user: LoginUser): Flow<NetWorkResponseState<LoginResponse>> {
//        return flow {
//            try {
//                emit(NetWorkResponseState.Loading)
//                val response = apiService.loginUser(user)
//                emit(NetWorkResponseState.Success(response))
//            } catch (e: Exception) {
//                emit(NetWorkResponseState.Error(e))
//            }
//        }
//    }
//
//    override fun loginPatient(user: LoginUser): Flow<NetWorkResponseState<LoginResponse>> {
//        return flow {
//            try {
//                emit(NetWorkResponseState.Loading)
//                val response = apiService.loginPatient(user)
//                emit(NetWorkResponseState.Success(response))
//            } catch (e: Exception) {
//                emit(NetWorkResponseState.Error(e))
//            }
//        }
//    }
//
//    override fun signUpPatient(user: SignUpPatient): Flow<NetWorkResponseState<SignUpResponse>> {
//        return flow {
//            try {
//                emit(NetWorkResponseState.Loading)
//                val response = apiService.signUpPatient(user)
//                emit(NetWorkResponseState.Success(response))
//            } catch (e: Exception) {
//                emit(NetWorkResponseState.Error(e))
//            }
//        }
//    }
//
//    override fun signUpDoctor(user: SignUpDoctor): Flow<NetWorkResponseState<SignUpResponse>> {
//        return flow {
//            try {
//                emit(NetWorkResponseState.Loading)
//                val response = apiService.signUpUser(user)
//                emit(NetWorkResponseState.Success(response))
//            } catch (e: Exception) {
//                emit(NetWorkResponseState.Error(e))
//            }
//        }
//    }
}