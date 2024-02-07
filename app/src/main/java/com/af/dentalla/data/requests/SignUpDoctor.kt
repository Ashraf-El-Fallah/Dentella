package com.af.dentalla.data.requests

import com.google.gson.annotations.SerializedName

data class SignUpDoctor(
    val userName: String,
    val email: String,
    val dentistID: String,
    val password: String,
    val phone: String
)
