package com.af.dentalla.data.remote.requests
/*
TODO: use kotlix.serlization or Moshi (I think it's better than gson)
TODO: See (https://medium.com/@prakash.ayinala7/getting-started-with-room-database-in-kotlin-jetpack-compose-mvvm-dagger-hilt-3bdec10b70ed)
 */
class LoginPatient(
    passWord: String,
    userName: String
) : LoginUser(email = null, passWord, userName)