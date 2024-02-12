package com.af.dentalla.domain.usecase.authentication.signup

import com.af.dentalla.data.remote.requests.SignUpUser
import com.af.dentalla.domain.repository.PatientRepository
import javax.inject.Inject

class SignUpPatientUseCase @Inject constructor(
    private val repository: PatientRepository
) {
    operator fun invoke(
        signUpPatient: SignUpUser
    ) = repository.signUpPatient(signUpPatient)
}