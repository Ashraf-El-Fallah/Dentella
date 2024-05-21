package com.af.dentalla.data.remote

import android.content.Context
import android.content.Intent
import android.util.Log
import com.af.dentalla.data.local.DataStorePreferencesService
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

//        val token =
//            "yJ9.eyJW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiZGRkIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbmFtZWlkZW50aWZpZXIiOiJkMzQ0YzNlMy03YzAxLTQ0NDQtYTZkYi1hZjc3NGZmMWZlNzEiLCJqdGkiOiJhMmJlMzZlYy0zY2JkLTRkMDAtOGFjZC1mMGMyOGI0NGVlZDUiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJEb2N0b3IiLCJleHAiOjE3MTYyOTQ2OTYsImlzcyI6Imh0dHA6Ly9kZW50ZWxsYS5zb21lZS5jb20vIiwiYXVkIjoiaHR0cHM6Ly9sb2NhbGhvc3Q6NDIwMC8ifQ.qsNfy_85M8F4TBqI7EiYOFJBZU7lidD_2JIdOAmRSY8"

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




