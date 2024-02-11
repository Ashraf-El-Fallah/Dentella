package com.af.dentalla.domain.repository

interface DoctorRepository {

    suspend fun loginDoctor(
        email: String,
        password: String
    ): Boolean

    suspend fun signUpDoctor(
        userName: String,
        email: String,
        phone: String,
        password: String,
        id: String
    ): Boolean

}