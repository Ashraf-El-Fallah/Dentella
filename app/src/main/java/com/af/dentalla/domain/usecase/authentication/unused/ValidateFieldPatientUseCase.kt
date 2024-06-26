package com.af.dentalla.domain.usecase.authentication.unused

import javax.inject.Inject

class ValidateFieldPatientUseCase @Inject constructor(
    private val validateUserNameFieldUseCase: ValidateUserNameFieldUseCase,
    private val validatePasswordFieldUseCase: ValidatePasswordFieldUseCase
) {
    operator fun invoke(userName: String, password: String) =
        validateUserNameFieldUseCase(userName).isValid() && validatePasswordFieldUseCase(password).isValid()
}