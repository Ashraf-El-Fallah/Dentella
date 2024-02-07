package com.af.dentalla.data.source.remote

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.api.ApiService
import com.af.dentalla.data.dto.LoginResponse
import com.af.dentalla.data.dto.SignUpResponse
import com.af.dentalla.data.requests.LoginUser
import com.af.dentalla.data.requests.SignUpDoctor
import com.af.dentalla.data.requests.SignUpPatient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    override fun loginDoctor(user: LoginUser): Flow<NetWorkResponseState<LoginResponse>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)
                val response = apiService.loginDoctor(user)
                emit(NetWorkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun loginPatient(user: LoginUser): Flow<NetWorkResponseState<LoginResponse>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)
                val response = apiService.loginPatient(user)
                emit(NetWorkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun signUpPatient(user: SignUpPatient): Flow<NetWorkResponseState<SignUpResponse>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)
                val response = apiService.signUpPatient(user)
                emit(NetWorkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun signUpDoctor(user: SignUpDoctor): Flow<NetWorkResponseState<SignUpResponse>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)
                val response = apiService.signUpDoctor(user)
                emit(NetWorkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }
}