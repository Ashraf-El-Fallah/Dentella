package com.af.dentalla.data.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.LoginUser
import com.af.dentalla.data.dto.SignUpUser
import com.af.dentalla.domain.entity.LoginResponseEntity
import com.af.dentalla.domain.entity.SignUpResponseEntity
import com.af.dentalla.domain.repository.DoctorRepository
import kotlinx.coroutines.flow.Flow

class DoctorRepositoryImpl : DoctorRepository {
    override fun login(loginUser: LoginUser): Flow<NetWorkResponseState<LoginResponseEntity>> {
        TODO("Not yet implemented")
    }

    override fun signUp(signUpUser: SignUpUser): Flow<NetWorkResponseState<SignUpResponseEntity>> {
        TODO("Not yet implemented")
    }

}