package com.af.dentalla.ui.auth.login

import com.af.dentalla.utilities.FormFieldState

data class LoginState(
    val userNameState: FormFieldState = FormFieldState.Valid,
    val emailState: FormFieldState = FormFieldState.Valid,
    val password: FormFieldState = FormFieldState.Valid
)
