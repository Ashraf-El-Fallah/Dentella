package com.af.dentalla.data.remote.requests

data class LoginPatient(
    val userName: String,
    val password: String
) : LoginUser