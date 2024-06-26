package com.af.dentalla.domain.usecase.authentication.validations

import javax.inject.Inject

class ValidateIdUseCase @Inject constructor() {
    operator fun invoke(id: String) =
        id.isEmpty() || id.length !in 5..10
}