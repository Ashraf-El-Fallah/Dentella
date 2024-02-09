package com.af.dentalla.domain.usecase.authentication.login

import com.af.dentalla.domain.repository.PatientRepository
import javax.inject.Inject

class LoginPatientUseCase @Inject constructor(
    private val repository: PatientRepository
) {
    suspend operator fun invoke(userName: String, password: String) =
        repository.loginPatient(userName, password)
}