package com.af.dentalla.domain.user.signup

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.SignUpUser
import com.af.dentalla.domain.entity.SignUpResponseEntity
import kotlinx.coroutines.flow.Flow

interface SignUpUseCase {
    operator fun invoke(signUpUser: SignUpUser): Flow<NetWorkResponseState<SignUpResponseEntity>>
}