package com.af.dentalla.domain.usecase.authentication.signup

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.domain.entity.SignUpEntity
import com.af.dentalla.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpPatientUseCase @Inject constructor(
    private val repository: PatientRepository
) {
    suspend operator fun invoke(
        userName: String,
        email: String,
        phone: String,
        password: String
    ) = repository.signUpPatient(userName, email, phone, password)
}