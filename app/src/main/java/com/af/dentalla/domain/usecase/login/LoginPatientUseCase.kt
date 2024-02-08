package com.af.dentalla.domain.usecase.login

import com.af.dentalla.domain.repository.PatientRepository
import javax.inject.Inject

class LoginPatientUseCase @Inject constructor(
    private val repository: PatientRepository
) {
    suspend operator fun invoke(userName: String, password: String): Boolean =
        repository.loginPatient(userName, password)
}