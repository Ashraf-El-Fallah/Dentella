package com.af.dentalla.data.remote

import android.util.Base64
import android.util.Log
import com.af.dentalla.data.local.DataStorePreferencesService
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(dataStorePreferencesService: DataStorePreferencesService) : Interceptor {
//    dataStorePreferencesService: DataStorePreferencesService
    private val token = dataStorePreferencesService.getToken()
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("interceptorKKKKKKKK",".........")
        Log.d("Token interceptor",token)
        val request = chain.request().newBuilder()
//            .addHeader("Authorization", "Basic MTExNjEwNzY6NjAtZGF5ZnJlZXRyaWFs")
            .addHeader("Authorization", "Bearer $token")
//            .addHeader("Content-Type", "application/json")
            .build()
//        }
        return chain.proceed(request)
    }
}

private fun isSignUpRequest(request: Request): Boolean {
    val pathSegments = request.url.encodedPathSegments
    return pathSegments.containsAll(
        listOf(
            "api",
            "Account"
        )
    ) && pathSegments.any { it == "doctor" || it == "patient" } && pathSegments.contains("register")
}
