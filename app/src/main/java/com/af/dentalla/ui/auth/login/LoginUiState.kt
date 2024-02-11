package com.af.dentalla.ui.auth.login

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val email: String = "",
    val emailHelperText: String = "",
    val userNameHelperText: String = "",
    val passwordHelperText: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val isValidForm: Boolean = false
)
