package com.af.dentalla.domain.usecase.authentication.validations

import javax.inject.Inject

class ValidatePasswordFieldUseCase @Inject constructor() {
    operator fun invoke(password: String) = password.length !in 8..15
}