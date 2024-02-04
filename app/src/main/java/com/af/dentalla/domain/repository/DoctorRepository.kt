package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.LoginUser
import com.af.dentalla.domain.entity.UserResponseEntity
import kotlinx.coroutines.flow.Flow

interface DoctorRepository {
    fun login(loginUser: LoginUser): Flow<NetWorkResponseState<UserResponseEntity>>
}