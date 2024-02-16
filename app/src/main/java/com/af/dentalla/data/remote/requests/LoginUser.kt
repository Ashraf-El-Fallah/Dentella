package com.af.dentalla.data.remote.requests

import com.google.gson.annotations.SerializedName

open class LoginUser(
    @SerializedName("email")
    val email: String? = null,

    @SerializedName("passWord")
    val password: String? = null,

    @SerializedName("userName")
    val username: String? = null
)