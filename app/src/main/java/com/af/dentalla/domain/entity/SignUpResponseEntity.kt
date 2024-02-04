package com.af.dentalla.domain.entity

data class SignUpResponseEntity(
    val fullName: String,
    val email: String,
    val idNumber: String,
    val phone: String,
    val password: String
)
