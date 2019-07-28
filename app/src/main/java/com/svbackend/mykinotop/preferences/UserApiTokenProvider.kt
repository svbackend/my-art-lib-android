package com.svbackend.mykinotop.preferences

const val API_TOKEN_KEY = "api_token"

interface UserApiTokenProvider {
    fun getApiToken(): String?
    fun setApiToken(apiToken: String)
}