package com.af.dentalla.ui.auth.login

import com.af.dentalla.utilities.AccountManager

sealed interface LoginUIEvent {
    data class LoginEvent(val accountType: AccountManager.AccountType?) : LoginUIEvent
    data class SignUpEvent(val accountType: AccountManager.AccountType?) : LoginUIEvent
}