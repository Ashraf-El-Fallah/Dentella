package com.af.dentalla.domain.usecase.authentication.validations

import javax.inject.Inject

class ValidatePasswordAndConfirmationUseCase @Inject constructor(
    private val validatePasswordFieldUseCase: ValidatePasswordFieldUseCase
) {
    operator fun invoke(password: String, confirmPassword: String) =
        (validatePasswordFieldUseCase(password) || password != confirmPassword)
}