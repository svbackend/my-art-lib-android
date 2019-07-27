package com.svbackend.mykinotop.dto.registration


import com.google.gson.annotations.SerializedName

data class RegistrationData(
    val email: String,
    val username: String,
    val password: String
)