package com.af.dentalla.domain.usecase.authentication

import com.af.dentalla.utils.FormFieldState
import javax.inject.Inject

class ValidateDoctorIdUseCase @Inject constructor() {
    operator fun invoke(id: String): FormFieldState {
        if (id.length < 4) {
            return FormFieldState.InValid("Id is too Short")
        }
        return FormFieldState.Valid
    }
}