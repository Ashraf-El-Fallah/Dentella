package com.af.dentalla.utils

import android.util.Patterns

//object ValidationUtils {


object ValidationUtils {
    fun isUserNameNotValid(userName: String) = (userName.isBlank() || userName.isEmpty())
    fun isEmailNotValid(email: String) =
        (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())

    fun isPhoneNumberNotValid(phoneNumber: String) =
        (phoneNumber.length != 11 || phoneNumber.isEmpty())

    fun isIdNotValid(id: String) = (id.length <= 4 || id.isEmpty() || id.length > 10)
    fun isPasswordNotValid(password: String) = password.length < 8
    fun isPasswordAndConfirmationNotEqual(password: String, confirmPassword: String) =
        (password.length < 8 || password.length > 15 || password != confirmPassword)

    ///////////////////////////////////////////////////////////////////////////////////new////
    fun isUserNameValid(userName: String): Boolean = !(userName.isBlank() || userName.isEmpty())
    fun isEmailValid(email: String): Boolean =
        !(email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())

    fun isPhoneNumberValid(phoneNumber: String): Boolean =
        !(phoneNumber.length != 11 || phoneNumber.isEmpty())

    fun isIdValid(id: String): Boolean = !(id.length <= 4 || id.isEmpty() || id.length > 10)
    fun isPasswordValid(password: String): Boolean = password.length >= 8
    fun isPasswordAndConfirmationEqual(password: String, confirmPassword: String): Boolean =
        password.length in 8..15 && password == confirmPassword
}

