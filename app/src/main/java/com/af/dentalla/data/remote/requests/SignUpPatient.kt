package com.af.dentalla.data.remote.requests

class SignUpPatient(
    email: String,
    password: String,
    username: String,
    phoneNumber: String
) : SignUpUser(email, password, phoneNumber, username, id = null)