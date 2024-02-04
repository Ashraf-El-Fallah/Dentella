package com.af.dentalla.domain.user

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.User
import com.af.dentalla.domain.entity.UserResponseEntity
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    operator fun invoke(user: User): Flow<NetWorkResponseState<UserResponseEntity>>
}