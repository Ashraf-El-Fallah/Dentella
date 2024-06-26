package com.af.dentalla.data.mapper

import com.af.dentalla.data.remote.dto.UserProfileInformationDto
import com.af.dentalla.domain.entity.ProfileInformationEntity
import com.af.dentalla.domain.mapper.BaseMapper
import javax.inject.Inject

class ProfileInformationMapper @Inject constructor() :
    BaseMapper<UserProfileInformationDto, ProfileInformationEntity> {
    override fun map(input: UserProfileInformationDto): ProfileInformationEntity {
        return ProfileInformationEntity(
            bio = input.bio ?: "",
            currentUniversity = input.currentUniversity ?: "",
            email = input.email ?: "",
            phoneNumber = input.phoneNumber ?: "",
            photo = input.photo ?: "",
            userName = input.userName ?: ""
        )
    }
}