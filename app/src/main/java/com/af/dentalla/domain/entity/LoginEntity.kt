package com.af.dentalla.domain.entity

data class LoginEntity(
    val token: String,
    val tokenExpiration: String
)
