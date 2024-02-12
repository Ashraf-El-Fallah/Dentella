package com.af.dentalla.domain.usecase.authentication.login

import com.af.dentalla.data.remote.requests.LoginPatient
import com.af.dentalla.domain.repository.PatientRepository
import javax.inject.Inject

class LoginPatientUseCase @Inject constructor(
    private val repository: PatientRepository
) {
    operator fun invoke(loginPatient: LoginPatient) =
        repository.loginPatient(loginPatient)
}