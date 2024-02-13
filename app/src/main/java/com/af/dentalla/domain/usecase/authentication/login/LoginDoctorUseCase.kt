package com.af.dentalla.domain.usecase.authentication.login

import com.af.dentalla.data.remote.requests.LoginDoctor
import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class LoginDoctorUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(loginDoctor: LoginDoctor) =
        repository.loginUser(loginDoctor)
}