package com.af.dentalla.data.remote.requests

import com.google.gson.annotations.SerializedName


class SignUpDoctor(
    email: String,
    password: String,
    username: String,
    phoneNumber: String,
    id: String
) : SignUpUser(email, password, phoneNumber, username,id)

