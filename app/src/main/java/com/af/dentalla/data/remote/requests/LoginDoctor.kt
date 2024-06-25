package com.af.dentalla.data.remote.requests

class LoginDoctor(
    email: String,
    passWord: String?
) : LoginUser(email, passWord, username = null)
