package com.af.dentalla.domain.usecase.login

import javax.inject.Inject

class ValidateFieldUseCase @Inject constructor(
    private val validateLoginFieldUseCase: ValidateLoginFieldUseCase,
    private val validatePasswordFieldUseCase: ValidatePasswordFieldUseCase
) {
    operator fun invoke(userName: String, password: String) =
        validateLoginFieldUseCase(userName).isValid() && validatePasswordFieldUseCase(password).isValid()
}