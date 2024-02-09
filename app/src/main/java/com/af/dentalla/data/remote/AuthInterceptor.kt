package com.af.dentalla.data.remote

import com.af.dentalla.data.local.DataStorePreferencesService
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(dataStorePreferencesService: DataStorePreferencesService) :
    Interceptor {

    private val token = dataStorePreferencesService.getToken()
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}