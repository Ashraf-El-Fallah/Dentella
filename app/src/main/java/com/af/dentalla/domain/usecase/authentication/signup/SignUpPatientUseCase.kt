package com.af.dentalla.domain.usecase.authentication.signup

import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.data.remote.requests.SignUpUser
import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class SignUpPatientUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(
        signUpPatient: SignUpUser
    ) = repository.signUpUser(signUpPatient)
}