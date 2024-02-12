package com.af.dentalla.data.remote.requests

data class LoginDoctor(
    val email: String,
    val password: String
) : LoginUser
