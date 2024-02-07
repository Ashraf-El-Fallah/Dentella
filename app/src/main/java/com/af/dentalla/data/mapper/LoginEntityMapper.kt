package com.af.dentalla.data.mapper

import com.af.dentalla.data.dto.LoginResponse
import com.af.dentalla.domain.entity.LoginEntity
import javax.inject.Inject

class LoginEntityMapper @Inject constructor() : BaseMapper<LoginResponse, LoginEntity> {
//    fun LoginResponse.toLoginEntity(): LoginEntity {
//        return LoginEntity(token, tokenExpiration)
//    }

    override fun map(input: LoginResponse): LoginEntity {
        return LoginEntity(
            token = input.token,
            tokenExpiration = input.tokenExpiration
        )
    }
}