package com.af.dentalla.domain.usecase.doctor.login

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.requests.LoginUser
import com.af.dentalla.domain.entity.LoginEntity
import com.af.dentalla.domain.repository.DoctorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginDoctorUseCaseImpl @Inject constructor(
    private val repository: DoctorRepository
) : LoginDoctorUseCase {
    override fun invoke(loginUser: LoginUser): Flow<NetWorkResponseState<LoginEntity>> {
        return repository.loginDoctor(loginUser)
    }

}