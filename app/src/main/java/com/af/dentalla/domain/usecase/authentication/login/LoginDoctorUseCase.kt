package com.af.dentalla.domain.usecase.authentication.login

import com.af.dentalla.domain.repository.DoctorRepository
import javax.inject.Inject

class LoginDoctorUseCase @Inject constructor(
    private val repository: DoctorRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        repository.loginDoctor(email, password)
}