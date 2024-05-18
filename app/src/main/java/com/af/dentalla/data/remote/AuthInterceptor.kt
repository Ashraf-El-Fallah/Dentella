package com.af.dentalla.data.remote

import android.util.Log
import com.af.dentalla.data.local.DataStorePreferencesService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val dataStorePreferencesService: DataStorePreferencesService) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token: String? = runBlocking {
            try {
                dataStorePreferencesService.getToken()
            } catch (e: Exception) {
                Log.e("AuthInterceptor", "Failed to retrieve token: ${e.message}")
                null
            }
        }

        return if (token != null) {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        } else {
            Log.e("AuthInterceptor", "Token is null. Proceeding without Authorization header...")
            chain.proceed(chain.request())
        }

//      If token is null, handle the case here (e.g., redirect to login screen)
        Log.e("AuthInterceptor", "Token is null. Handling the case...")
        val responseBody = ResponseBody.create(
            "application/json".toMediaTypeOrNull(),
            "{\"error\":\"Unauthorized\"}"
        )
        return Response.Builder()
            .request(chain.request())
            .code(401)
            .protocol(Protocol.HTTP_1_1)
            .message("Unauthorized")
            .body(responseBody)
            .build()

//        Log.e("AuthInterceptor", "Token is null. Handling the case...")
//        return Response.Builder()
//            .code(401)
//            .message("Unauthorized")
//            .protocol(Protocol.HTTP_1_1)
//            .build()
//    }


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
}