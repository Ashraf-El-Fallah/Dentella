package com.af.dentalla.domain.usecase.authentication.signup

import com.af.dentalla.utils.FormFieldState
import javax.inject.Inject

class ValidatePasswordAndConfirmation @Inject constructor() {
    operator fun invoke(password: String, confirmPassword: String): FormFieldState {
        if (password.length < 8 || confirmPassword.length < 8 || password != confirmPassword) {
            return FormFieldState.InValid("Password is invalid")
        }
        return FormFieldState.Valid
    }
}