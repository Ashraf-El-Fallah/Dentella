package com.af.dentalla.domain.user.login

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.LoginUser
import com.af.dentalla.domain.entity.LoginResponseEntity
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    operator fun invoke(loginUser: LoginUser): Flow<NetWorkResponseState<LoginResponseEntity>>
}