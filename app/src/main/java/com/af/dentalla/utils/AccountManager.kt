package com.af.dentalla.utils

object AccountManager {
    var accountType: AccountType? = null

    enum class AccountType {
        PATIENT,
        DOCTOR
    }
}