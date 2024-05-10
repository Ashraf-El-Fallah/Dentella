package com.af.dentalla.domain.usecase.doctor

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.ProfileInformationDto
import com.af.dentalla.domain.entity.ProfileInformationEntity
import com.af.dentalla.domain.mapper.BaseMapper
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.utils.mapResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDoctorProfileInformationUseCase @Inject constructor(
    private val repository: UserRepository,
    private val profileInformationEntity: BaseMapper<ProfileInformationDto, ProfileInformationEntity>
) {
    operator fun invoke(): Flow<NetWorkResponseState<ProfileInformationEntity>> {
        return repository.returnProfileInformation().map {
            it.mapResponse {
                profileInformationEntity.map(this)
            }
        }
    }
}