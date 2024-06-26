package com.af.dentalla.domain.usecase.authentication.validations

import javax.inject.Inject

class ValidateUserNameFieldUseCase @Inject constructor() {
    operator fun invoke(userName: String) = (userName.isBlank() || userName.isEmpty())
}