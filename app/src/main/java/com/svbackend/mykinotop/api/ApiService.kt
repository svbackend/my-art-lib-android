package com.svbackend.mykinotop.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.svbackend.mykinotop.dto.login.LoginRequest
import com.svbackend.mykinotop.dto.login.LoginResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


const val API_HOST = "https://api.mykino.top"

// https://api.mykino.top

interface ApiService {

    @POST("/api/auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Deferred<LoginResponse>

    companion object {
        operator fun invoke(): ApiService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("language", "en") // todo load lang from user's preferences
                    .build()

                // todo if authorized addQueryParameter("token", "USER's TOKEN")

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                // .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(API_HOST)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}