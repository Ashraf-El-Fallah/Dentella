package com.af.dentalla.domain.user

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.LoginUser
import com.af.dentalla.domain.entity.UserResponseEntity
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    operator fun invoke(loginUser: LoginUser): Flow<NetWorkResponseState<UserResponseEntity>>
}