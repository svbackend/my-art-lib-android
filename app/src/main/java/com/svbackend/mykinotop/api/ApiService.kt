package com.svbackend.mykinotop.api

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.svbackend.mykinotop.dto.login.LoginRequest
import com.svbackend.mykinotop.dto.login.LoginResponse
import com.svbackend.mykinotop.dto.movie.MoviesResponse
import com.svbackend.mykinotop.dto.registration.RegistrationRequest
import com.svbackend.mykinotop.dto.registration.RegistrationResponse
import com.svbackend.mykinotop.preferences.UserApiTokenProvider
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


const val WEB_HOST = "https://mykino.top"
const val API_HOST = "https://api.mykino.top"
// const val API_HOST = "http://10.0.2.2:8080" // 10.0.2.2 bcz JVM map this ip to localhost on your host machine

// https://api.mykino.top

interface ApiService {

    @POST("/api/auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Deferred<LoginResponse>

    @POST("/api/users")
    fun register(
        @Body registrationRequest: RegistrationRequest
    ): Deferred<RegistrationResponse>

    @GET("/api/users/username/{username}")
    fun isUsernameAvailable(@Path("username") username: String): Deferred<EmptyResponse>

    @GET("/api/users/email/{email}")
    fun isEmailAvailable(@Path("email") email: String): Deferred<EmptyResponse>

    @GET("/api/movies")
    fun getMovies(@Query("offset") offset: Int = 0): Call<MoviesResponse>

    companion object {
        operator fun invoke(apiTokenProvider: UserApiTokenProvider): ApiService {
            val requestInterceptor = Interceptor { chain ->
                val urlBuilder = chain.request()
                    .url()
                    .newBuilder()

                val apiToken = apiTokenProvider.getApiToken()

                if (apiToken != null) {
                    urlBuilder.addQueryParameter("api_token", apiToken)
                }

                urlBuilder.addQueryParameter("language", "en") // todo load lang from user's preferences

                val url = urlBuilder.build()

                Log.d("API_SERVICE", url.toString())

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