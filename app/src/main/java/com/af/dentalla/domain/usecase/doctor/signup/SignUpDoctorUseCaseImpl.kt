package com.af.dentalla.domain.usecase.doctor.signup

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.requests.SignUpDoctor
import com.af.dentalla.data.requests.SignUpPatient
import com.af.dentalla.domain.entity.SignUpEntity
import com.af.dentalla.domain.repository.DoctorRepository
import com.af.dentalla.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpDoctorUseCaseImpl @Inject constructor(
    private val repository: DoctorRepository
) : SignUpDoctorUseCase {
    override suspend fun invoke(signUpDoctor: SignUpDoctor): Flow<NetWorkResponseState<SignUpEntity>> {
        return repository.signUpDoctor(signUpDoctor)
    }

}