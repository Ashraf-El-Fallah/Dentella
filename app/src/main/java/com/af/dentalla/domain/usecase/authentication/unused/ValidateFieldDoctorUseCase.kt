package com.af.dentalla.domain.usecase.authentication.unused

import javax.inject.Inject

class ValidateFieldDoctorUseCase @Inject constructor(
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val validatePasswordFieldUseCase: ValidatePasswordFieldUseCase
) {
    operator fun invoke(email: String, password: String) =
        validateEmailFieldUseCase(email).isValid() && validatePasswordFieldUseCase(password).isValid()
}