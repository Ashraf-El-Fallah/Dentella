package com.af.dentalla.domain.usecase.authentication.signup

import com.af.dentalla.data.remote.requests.SignUpDoctor
import com.af.dentalla.domain.repository.DoctorRepository
import javax.inject.Inject
import kotlin.math.sign

class SignUpDoctorUseCase @Inject constructor(
    private val repository: DoctorRepository
) {
    operator fun invoke(signUpDoctor: SignUpDoctor) = repository.signUpDoctor(signUpDoctor)
}