package com.af.dentalla.data.remote.dto


import com.google.gson.annotations.SerializedName

/*
TODO: use kotlix.serlization or Moshi (I think it's better than gson)
TODO: See (https://medium.com/@prakash.ayinala7/getting-started-with-room-database-in-kotlin-jetpack-compose-mvvm-dagger-hilt-3bdec10b70ed)
 */

data class PostDtoItem(
    @SerializedName("content")
    val content: String?,
    @SerializedName("patientName")
    val patientName: String?,
    @SerializedName("patientPhoto")
    val patientPhoto: Any?,
    @SerializedName("postId")
    val postId: Int?
)