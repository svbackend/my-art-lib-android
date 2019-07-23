package com.svbackend.mykinotop.dto.login


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("api_token")
    val apiToken: String,
    @SerializedName("user_id")
    val userId: Int
)