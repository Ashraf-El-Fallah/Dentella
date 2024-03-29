package com.af.dentalla.utilities

import android.os.Message

sealed class FormFieldState {
    object Valid : FormFieldState()

    data class InValid(val message: String) : FormFieldState()

    fun errorMessage() = if (this is InValid) message else null

    fun isValid(): Boolean {
        return this is Valid
    }

}
