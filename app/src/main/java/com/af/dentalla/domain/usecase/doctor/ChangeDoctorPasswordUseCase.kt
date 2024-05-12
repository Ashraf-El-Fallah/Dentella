package com.af.dentalla.domain.usecase.doctor

import com.af.dentalla.data.remote.requests.DoctorPassword
import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class ChangeDoctorPasswordUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(
        doctorPassword: DoctorPassword
    ) = repository.changeDoctorPassword(doctorPassword)
}