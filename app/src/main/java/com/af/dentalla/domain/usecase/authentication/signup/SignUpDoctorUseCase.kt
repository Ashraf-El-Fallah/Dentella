package com.af.dentalla.domain.usecase.authentication.signup

import com.af.dentalla.domain.repository.DoctorRepository
import javax.inject.Inject

class SignUpDoctorUseCase @Inject constructor(
    private val repository: DoctorRepository
) {
    suspend operator fun invoke(
        userName: String,
        id: String,
        email: String,
        phone: String,
        password: String
    ) = repository.signUpDoctor(userName, email, phone, password, id)
}