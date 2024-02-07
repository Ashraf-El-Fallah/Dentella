package com.af.dentalla.data.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.LoginResponse
import com.af.dentalla.data.dto.SignUpResponse
import com.af.dentalla.data.mapper.BaseMapper
import com.af.dentalla.data.mapper.LoginEntityMapper
import com.af.dentalla.data.requests.LoginUser
import com.af.dentalla.data.requests.SignUpPatient
import com.af.dentalla.data.source.remote.RemoteDataSource
import com.af.dentalla.di.coroutine.IoDispatcher
import com.af.dentalla.domain.entity.LoginEntity
import com.af.dentalla.domain.entity.SignUpEntity
import com.af.dentalla.domain.repository.PatientRepository
import com.af.dentalla.utils.mapResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val loginEntityMapper: BaseMapper<LoginResponse, LoginEntity>,
    private val signUpEntityMapper: BaseMapper<SignUpResponse, SignUpEntity>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PatientRepository {
    override fun loginPatient(loginUser: LoginUser): Flow<NetWorkResponseState<LoginEntity>> =
        remoteDataSource.loginPatient(loginUser).map {
            it.mapResponse {
                loginEntityMapper.map(this)
            }
        }.flowOn(ioDispatcher)

    override fun signUpPatient(signUpPatient: SignUpPatient): Flow<NetWorkResponseState<SignUpEntity>> =
        remoteDataSource.signUpPatient(signUpPatient).map {
            it.mapResponse {
                signUpEntityMapper.map(this)
            }
        }.flowOn(ioDispatcher)

}