package com.af.dentalla.domain.usecase.authentication

import android.util.Patterns
import com.af.dentalla.utilities.FormFieldState
import javax.inject.Inject

class ValidateEmailFieldUseCase @Inject constructor() {
    operator fun invoke(email: String): FormFieldState {
        if (email.isNotEmpty() &&
            email.isNotBlank() &&
            Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return FormFieldState.Valid
        }
        return FormFieldState.InValid("Email is incorrect")
    }
}