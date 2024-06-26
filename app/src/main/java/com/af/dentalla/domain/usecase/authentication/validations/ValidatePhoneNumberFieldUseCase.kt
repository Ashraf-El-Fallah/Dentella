package com.af.dentalla.domain.usecase.authentication.validations

import javax.inject.Inject

class ValidatePhoneNumberFieldUseCase @Inject constructor() {
    operator fun invoke(phoneNumber: String) = (phoneNumber.length != 11 || phoneNumber.isEmpty())
}