package com.af.dentalla.domain.usecase.authentication.login

import com.af.dentalla.data.remote.requests.LoginDoctor
import com.af.dentalla.domain.repository.DoctorRepository
import javax.inject.Inject

class LoginDoctorUseCase @Inject constructor(
    private val repository: DoctorRepository
) {
    operator fun invoke(loginDoctor: LoginDoctor) =
        repository.loginDoctor(loginDoctor)
}