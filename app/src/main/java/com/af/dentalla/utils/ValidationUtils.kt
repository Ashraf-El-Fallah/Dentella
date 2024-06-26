package com.af.dentalla.utils

import android.util.Patterns

//object ValidationUtils {


object ValidationUtils {
    fun isUserNameNotValid(userName: String) = (userName.isBlank() || userName.isEmpty())
    fun isEmailNotValid(email: String) =
        (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())

    fun isPhoneNumberNotValid(phoneNumber: String) =
        (phoneNumber.length != 11 || phoneNumber.isEmpty())
}

