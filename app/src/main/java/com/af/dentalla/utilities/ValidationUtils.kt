package com.af.dentalla.utilities

import android.util.Patterns

object ValidationUtils {
    fun isUserNameNotValid(userName: String) = (userName.isBlank() || userName.isEmpty())
    fun isEmailNotValid(email: String) =
        (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())

    fun isPhoneNumberNotValid(phoneNumber: String) =
        (phoneNumber.length != 11 || phoneNumber.isEmpty())

    fun isIdNotValid(id: String) = (id.length <= 4 || id.isEmpty() || id.length > 10)

    fun isPasswordNotValid(password: String) = password.length < 3
    fun isPasswordAndConfirmationNotEqual(password: String, confirmPassword: String) =
        (password.length < 8 || password.length > 15 || password != confirmPassword)
}