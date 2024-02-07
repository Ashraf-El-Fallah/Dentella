package com.af.dentalla.domain.usecase.patient.login

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.requests.LoginUser
import com.af.dentalla.domain.entity.LoginEntity
import kotlinx.coroutines.flow.Flow

interface LoginPatientUseCase {
    operator fun invoke(loginUser: LoginUser): Flow<NetWorkResponseState<LoginEntity>>
}