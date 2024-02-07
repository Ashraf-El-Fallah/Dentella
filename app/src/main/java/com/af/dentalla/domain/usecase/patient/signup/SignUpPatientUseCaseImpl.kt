package com.af.dentalla.domain.usecase.patient.signup

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.requests.SignUpPatient
import com.af.dentalla.domain.entity.SignUpEntity
import com.af.dentalla.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpPatientUseCaseImpl @Inject constructor(
    private val repository: PatientRepository
) : SignUpPatientUseCase {
    override suspend fun invoke(signUpPatient: SignUpPatient): Flow<NetWorkResponseState<SignUpEntity>> {
        return repository.signUpPatient(signUpPatient)
    }

}