package com.svbackend.mykinotop.dto


import com.google.gson.annotations.SerializedName

data class LoginErrorResponse(
    val error: String,
    @SerializedName("error_description")
    val errorDescription: String
)