package com.af.dentalla.data.remote.requests

data class SignUpBaseRequest(
    val userName: String,
    val email: String,
    val password: String,
    val phone: String
)
