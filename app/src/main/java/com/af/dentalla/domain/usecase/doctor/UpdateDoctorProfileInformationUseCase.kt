package com.af.dentalla.domain.usecase.doctor

import com.af.dentalla.data.remote.requests.DoctorProfileInformation
import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class UpdateDoctorProfileInformationUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(
        doctorProfileInformation: DoctorProfileInformation
    ) = repository.updateDoctorProfileInformation(doctorProfileInformation)
}