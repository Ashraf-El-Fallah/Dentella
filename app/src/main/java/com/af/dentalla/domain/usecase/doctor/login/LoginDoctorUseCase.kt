package com.af.dentalla.domain.usecase.doctor.login

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.requests.LoginUser
import com.af.dentalla.domain.entity.LoginEntity
import kotlinx.coroutines.flow.Flow

interface LoginDoctorUseCase {
    operator fun invoke(loginUser: LoginUser): Flow<NetWorkResponseState<LoginEntity>>
}