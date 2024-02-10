package com.af.dentalla.data.mapper

import com.af.dentalla.data.remote.dto.SignUpResponse
import com.af.dentalla.domain.entity.SignUpEntity
import com.af.dentalla.domain.mapper.BaseMapper
import javax.inject.Inject

class SignUpEntityMapper @Inject constructor() : BaseMapper<SignUpResponse, SignUpEntity> {
    //    fun SignUpResponse.toSignUpEntity(): SignUpEntity {
//        return SignUpEntity(message)
//    }
    override fun map(input: SignUpResponse): SignUpEntity {
        return SignUpEntity(
            message = input.message
        )
    }
}