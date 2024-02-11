package com.af.dentalla.domain.usecase.authentication.login

import com.af.dentalla.domain.usecase.authentication.ValidateUserNameFieldUseCase
import javax.inject.Inject

class ValidateFieldPatientUseCase @Inject constructor(
    private val validateUserNameFieldUseCase: ValidateUserNameFieldUseCase,
    private val validatePasswordFieldUseCase: ValidatePasswordFieldUseCase
) {
    operator fun invoke(userName: String, password: String) =
        validateUserNameFieldUseCase(userName).isValid() && validatePasswordFieldUseCase(password).isValid()
}