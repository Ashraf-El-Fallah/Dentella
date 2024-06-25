package com.af.dentalla.domain.usecase.patient

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.entity.DoctorProfileEntity
import com.af.dentalla.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDoctorProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(cardId: Int): Flow<NetWorkResponseState<DoctorProfileEntity>> =
        repository.getDoctorProfileDetails(cardId)
}