package com.af.dentalla.ui.auth

sealed class SignUpResult {
    data class SignUpError(val error: String) : SignUpResult()

}
