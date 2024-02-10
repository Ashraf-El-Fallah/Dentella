package com.af.dentalla.data.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.SignUpDoctor
import com.af.dentalla.domain.entity.LoginEntity
import com.af.dentalla.domain.entity.SignUpEntity
import com.af.dentalla.domain.repository.DoctorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
//    private val remoteDataSource: RemoteDataSource,
//    private val loginEntityMapper: BaseMapper<LoginResponse, LoginEntity>,
//    private val signUpEntityMapper: BaseMapper<SignUpResponse, SignUpEntity>,
//    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DoctorRepository
{
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
    override fun loginDoctor(loginUser: LoginUser): Flow<NetWorkResponseState<LoginEntity>> {
        TODO("Not yet implemented")
    }

    override fun signUpDoctor(signUpDoctor: SignUpDoctor): Flow<NetWorkResponseState<SignUpEntity>> {
        TODO("Not yet implemented")
    }
}