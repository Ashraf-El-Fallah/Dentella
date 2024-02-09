package com.af.dentalla.domain.usecase.authentication

import com.af.dentalla.utilities.FormFieldState
import javax.inject.Inject

class ValidateUserNameFieldUseCase @Inject constructor() {
    operator fun invoke(text: String): FormFieldState {
        if (text.isBlank() || text.isEmpty()) {
            return FormFieldState.InValid("Required")
        }
        return FormFieldState.Valid
    }
}