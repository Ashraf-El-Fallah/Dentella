package com.af.dentalla.domain.usecase.login

import com.af.dentalla.utilities.FormFieldState
import javax.inject.Inject

class ValidatePasswordFieldUseCase @Inject constructor() {
    operator fun invoke(password: String): FormFieldState {
        if (password.length < 4) {
            return FormFieldState.InValid("Required")
        }
        return FormFieldState.Valid
    }
}