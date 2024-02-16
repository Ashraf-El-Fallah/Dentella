package com.af.dentalla.data.remote.requests

import com.google.gson.annotations.SerializedName


//data class SignUpPatient(
//    val email: String,
//    val userName: String,
//    val password: String,
//    val phoneNumber: String
//) : SignUpUser


class SignUpPatient(
    email: String,
    password: String,
    username: String,
    phoneNumber: String
) : SignUpUser(email, password, phoneNumber, username, id = null)