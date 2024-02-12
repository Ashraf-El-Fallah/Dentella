package com.af.dentalla.domain.usecase.authentication.signup

import com.af.dentalla.data.remote.requests.SignUpUser
import com.af.dentalla.domain.repository.DoctorRepository
import javax.inject.Inject

class SignUpDoctorUseCase @Inject constructor(
    private val repository: DoctorRepository
) {
    operator fun invoke(signUpDoctor: SignUpUser) = repository.signUpDoctor(signUpDoctor)
}