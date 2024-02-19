package com.af.dentalla.data.remote.requests

import com.google.gson.annotations.SerializedName

open class SignUpUser(
    @SerializedName("email")
    val email: String? = null,

    @SerializedName("passWord")
    val password: String? = null,

    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @SerializedName("userName")
    val username: String? = null,

    @SerializedName("dentistID")
    val id: String? = null
)