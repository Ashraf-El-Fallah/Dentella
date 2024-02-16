package com.af.dentalla.data.remote.requests

import com.google.gson.annotations.SerializedName

open class SignUpUser(
    @SerializedName("email")
    val email: String? = null,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("phone_number")
    val phoneNumber: String? = null,

    @SerializedName("username")
    val username: String? = null,

    @SerializedName("dentistID")
    val id: String? = null
)