package com.af.dentalla.data.mapper

import com.af.dentalla.data.remote.dto.LoginResponse
import com.af.dentalla.domain.entity.LoginEntity
import javax.inject.Inject



class LoginEntityMapper @Inject constructor() : BaseMapper<LoginResponse, LoginEntity> {


    override fun map(input: LoginResponse): LoginEntity {
        return LoginEntity(
            token = input.token,
            tokenExpiration = input.tokenExpiration
        )
    }
}