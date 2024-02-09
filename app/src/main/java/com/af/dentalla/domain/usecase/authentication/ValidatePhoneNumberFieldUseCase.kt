package com.af.dentalla.domain.usecase.authentication

import com.af.dentalla.utilities.FormFieldState
import javax.inject.Inject

class ValidatePhoneNumberFieldUseCase @Inject constructor() {
    operator fun invoke(phoneNumber: String): FormFieldState {
        if (phoneNumber.length == 11) {
            return FormFieldState.Valid
        }
        return FormFieldState.InValid("Phone Number should be 11 digits")
    }
}