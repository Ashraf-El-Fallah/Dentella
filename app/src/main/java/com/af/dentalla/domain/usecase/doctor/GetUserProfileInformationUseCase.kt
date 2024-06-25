package com.af.dentalla.domain.usecase.doctor

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.entity.ProfileInformationEntity
import com.af.dentalla.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileInformationUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<NetWorkResponseState<ProfileInformationEntity>> =
        repository.returnUserProfileInformation()
}