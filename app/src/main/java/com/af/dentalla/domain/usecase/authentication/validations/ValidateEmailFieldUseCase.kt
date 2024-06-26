package com.af.dentalla.domain.usecase.authentication.validations

import android.util.Patterns
import javax.inject.Inject

class ValidateEmailFieldUseCase @Inject constructor() {
    operator fun invoke(email: String) =
        email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
}