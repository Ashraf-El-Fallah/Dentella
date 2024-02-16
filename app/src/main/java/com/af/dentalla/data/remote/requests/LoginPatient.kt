package com.af.dentalla.data.remote.requests

class LoginPatient(
    passWord: String,
    userName: String
) : LoginUser(email = null, passWord, userName)