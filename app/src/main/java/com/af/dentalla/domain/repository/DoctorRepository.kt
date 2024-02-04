package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.LoginUser
import com.af.dentalla.data.dto.SignUpUser
import com.af.dentalla.domain.entity.LoginResponseEntity
import com.af.dentalla.domain.entity.SignUpResponseEntity
import kotlinx.coroutines.flow.Flow

interface DoctorRepository {
    fun login(loginUser: LoginUser): Flow<NetWorkResponseState<LoginResponseEntity>>

    fun signUp(signUpUser: SignUpUser): Flow<NetWorkResponseState<SignUpResponseEntity>>
}