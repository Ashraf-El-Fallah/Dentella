package com.af.dentalla.di.network

import com.af.dentalla.data.remote.AuthInterceptor
import com.af.dentalla.data.remote.api.ApiService
import com.getkeepsafe.relinker.BuildConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// TODO: you can move BASE_URL to the gradle or a different kotlin file
private const val BASE_URL = "https://dentella.somee.com/api/"

//another base url ..
//private const val BASE_URL = "http://dentelle.somee.com/api/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {

        // TODO: use provideLoggingInterceptor() instead of val logging = HttpLoggingInterceptor()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder().followRedirects(true)
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .retryOnConnectionFailure(true)
            .build()
    }


    /*
TODO: use kotlinx.serialization or Moshi (I think it's better than gson)
TODO: See (https://medium.com/@prakash.ayinala7/getting-started-with-room-database-in-kotlin-jetpack-compose-mvvm-dagger-hilt-3bdec10b70ed)
 */
    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    // TODO: remove unused code
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideRetrofitTest(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}
// TODO: remove unused code

//    @Singleton
//    @Provides
//    fun provideApiService(retrofit: Retrofit): ApiService {
//        return retrofit.create(ApiService::class.java)
//    }
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(
//        client: OkHttpClient,
//        gsonConverterFactory: GsonConverterFactory
//    ): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(gsonConverterFactory)
//            .build()
//
//    }
//
//    @Singleton
//    @Provides
//    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(authInterceptor)
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideGsonConverterFactory(): GsonConverterFactory {
//        return GsonConverterFactory.create()
//    }
//
//    @Singleton
//    @Provides
//    fun provideGson(): Gson {
//        return Gson()
//    }
