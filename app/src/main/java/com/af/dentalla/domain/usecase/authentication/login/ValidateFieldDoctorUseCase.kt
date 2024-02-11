package com.af.dentalla.domain.usecase.authentication.login

import com.af.dentalla.domain.usecase.authentication.ValidateEmailFieldUseCase
import javax.inject.Inject

class ValidateFieldDoctorUseCase @Inject constructor(
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val validatePasswordFieldUseCase: ValidatePasswordFieldUseCase
) {
    operator fun invoke(email: String, password: String) =
        validateEmailFieldUseCase(email).isValid() && validatePasswordFieldUseCase(password).isValid()
}