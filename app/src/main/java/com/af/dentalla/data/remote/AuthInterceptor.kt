package com.af.dentalla.data.remote

import android.content.Context
import android.content.Intent
import android.util.Log
import com.af.dentalla.data.local.datastore.DataStorePreferencesService
import com.af.dentalla.ui.auth.AuthenticationActivity
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStorePreferencesService: DataStorePreferencesService,
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token: String? = runBlocking {
            try {
                dataStorePreferencesService.getToken()
            } catch (e: Exception) {
                Log.e("AuthInterceptor", "Failed to retrieve token: ${e.message}")
                null
            }
        }

        val request = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            Log.e("AuthInterceptor", "Token is null. Proceeding without Authorization header...")
            chain.request()
        }
        val response = chain.proceed(request)

        if (response.code == 401) {
            runBlocking {
                dataStorePreferencesService.clearToken()
            }
            navigateToAuthenticationActivity()

            val responseBody = ResponseBody.create(
                "application/json".toMediaTypeOrNull(),
                "{\"error\":\"Unauthorized\"}"
            )
            return response.newBuilder()
                .code(401)
                .protocol(Protocol.HTTP_1_1)
                .message("Unauthorized")
                .body(responseBody)
                .build()
        }
        return response
    }

    private fun navigateToAuthenticationActivity() {
        val intent = Intent(context, AuthenticationActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
    }
}




