package com.svbackend.mykinotop.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.svbackend.mykinotop.dto.login.LoginRequest
import com.svbackend.mykinotop.dto.login.LoginResponse
import com.svbackend.mykinotop.dto.registration.RegistrationRequest
import com.svbackend.mykinotop.dto.registration.RegistrationResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


const val WEB_HOST = "https://mykino.top"
// const val API_HOST = "https://api.mykino.top"
const val API_HOST = "http://10.0.2.2:8080" // 10.0.2.2 bcz JVM map this ip to localhost on your host machine

// https://api.mykino.top

interface ApiService {

    @POST("/api/auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Deferred<LoginResponse>

    @POST("/api/users")
    fun register(
        @Body registrationRequext: RegistrationRequest
    ): Deferred<RegistrationResponse>

    @GET("/api/users/username/{username}")
    fun isUsernameAvailable(@Path("username") username: String): Deferred<EmptyResponse>

    @GET("/api/users/email/{email}")
    fun isEmailAvailable(@Path("email") email: String): Deferred<EmptyResponse>

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