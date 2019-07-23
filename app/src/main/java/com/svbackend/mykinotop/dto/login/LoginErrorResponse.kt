package com.svbackend.mykinotop.dto.login


import com.google.gson.annotations.SerializedName

data class LoginErrorResponse(
    val error: String,
    @SerializedName("error_description")
    val errorDescription: String
)