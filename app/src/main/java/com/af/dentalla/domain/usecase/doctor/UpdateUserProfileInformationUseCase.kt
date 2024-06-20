package com.af.dentalla.domain.usecase.doctor

import com.af.dentalla.data.remote.requests.UserProfileInformation
import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserProfileInformationUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(
        userProfileInformation: UserProfileInformation
    ) = repository.updateUserProfileInformation(userProfileInformation)
}