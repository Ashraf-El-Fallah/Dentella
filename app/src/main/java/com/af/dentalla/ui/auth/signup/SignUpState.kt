package com.af.dentalla.ui.auth.signup

import android.icu.text.DateIntervalFormat.FormattedDateInterval
import com.af.dentalla.utilities.FormFieldState

data class SignUpState(
    val emailState: FormFieldState = FormFieldState.Valid,
    val userNameState: FormFieldState = FormFieldState.Valid,
    val phoneNumberState: FormFieldState = FormFieldState.Valid,
    val passwordState: FormFieldState = FormFieldState.Valid
)