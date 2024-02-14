package com.af.dentalla.domain.usecase.authentication.login

import com.af.dentalla.data.remote.requests.LoginPatient
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(loginUser: LoginUser) =
        repository.loginUser(loginUser)
}