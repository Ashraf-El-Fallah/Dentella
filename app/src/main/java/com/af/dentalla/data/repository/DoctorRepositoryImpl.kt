package com.af.dentalla.data.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.LoginUser
import com.af.dentalla.domain.entity.UserResponseEntity
import com.af.dentalla.domain.repository.DoctorRepository
import kotlinx.coroutines.flow.Flow

class DoctorRepositoryImpl : DoctorRepository {
    override fun login(loginUser: LoginUser): Flow<NetWorkResponseState<UserResponseEntity>> {
        TODO("Not yet implemented")
    }

}