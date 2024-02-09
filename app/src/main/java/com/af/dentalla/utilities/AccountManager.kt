package com.af.dentalla.utilities

object AccountManager {
    var accountType: AccountType? = null

    enum class AccountType {
        PATIENT,
        DOCTOR
    }
}