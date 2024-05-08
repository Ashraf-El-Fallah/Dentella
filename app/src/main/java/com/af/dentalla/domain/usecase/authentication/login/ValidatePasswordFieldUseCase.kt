package com.af.dentalla.domain.usecase.authentication.login

import com.af.dentalla.utils.FormFieldState
import javax.inject.Inject

class ValidatePasswordFieldUseCase @Inject constructor() {
    operator fun invoke(password: String): FormFieldState {
        if (password.length < 8) {
            return FormFieldState.InValid("Password is invalid")
        }
        return FormFieldState.Valid
    }
}