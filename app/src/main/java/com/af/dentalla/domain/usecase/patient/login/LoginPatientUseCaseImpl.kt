package com.af.dentalla.domain.usecase.patient.login

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.requests.LoginUser
import com.af.dentalla.domain.entity.LoginEntity
import com.af.dentalla.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginPatientUseCaseImpl @Inject constructor(
    private val repository: PatientRepository
) : LoginPatientUseCase {
    override fun invoke(loginUser: LoginUser): Flow<NetWorkResponseState<LoginEntity>> {
        return repository.loginPatient(loginUser)
    }

}