package com.af.dentalla.domain.usecase.patient

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.domain.entity.DoctorProfileEntity
import com.af.dentalla.domain.mapper.BaseMapper
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.utils.mapResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDoctorProfileUseCase @Inject constructor(
    private val doctorProfileEntity: BaseMapper<DoctorProfileDto, DoctorProfileEntity>,
    private val repository: UserRepository
) {
    operator fun invoke(cardId: Int): Flow<NetWorkResponseState<DoctorProfileEntity>> {
        return repository.getDoctorProfileDetails(cardId).map {
            it.mapResponse {
                doctorProfileEntity.map(this)
            }
        }
    }
}