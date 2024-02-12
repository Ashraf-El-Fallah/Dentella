package com.af.dentalla.data.remote.requests

import com.google.gson.annotations.SerializedName

data class SignUpPatient(
    val userName: String,
    val email: String,
    val password: String,
    val phone: String
) : SignUpUser