package com.af.dentalla.utils

import android.util.Patterns

object Validation {
    fun validateUserName(userName: String): Boolean {
        var error: String? = null
        if (userName.isEmpty()) {
            error = "full name is required"
        }
        return error == null
    }

    fun validateID(idNumber: String): Boolean {
        var error: String? = null
        if (idNumber.isEmpty()) {
            error = "ID is required"
        }
        return error == null
    }

    fun validateEmail(email: String): Boolean {
        var error: String? = null
        if (email.isEmpty()) {
            error = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            error = "Email is invalid"
        }
        return error == null
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        var error: String? = null
        if (phoneNumber.isEmpty()) {
            error = "phone number is required"
        } else if (phoneNumber.length < 11 || phoneNumber.length > 11) {
            error = "phone number must be 11 digits"
        }
        return error == null
    }

    fun validatePassword(password: String): Boolean {
        var error: String? = null
        if (password.isEmpty()) {
            error = "password is required"
        } else if (password.length < 15 || password.length > 15) {
            error = "password length must be 15"
        }
        return error == null
    }

    fun arePasswordsTheSame(password: String, confirmationPassword: String) =
        password == confirmationPassword
}