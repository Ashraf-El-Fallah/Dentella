package com.af.dentalla.data.remote.requests


data class SignUpDoctor(
    val userName: String,
    val email: String,
    val dentistID: String,
    val password: String,
    val phone: String
) : SignUpUser
