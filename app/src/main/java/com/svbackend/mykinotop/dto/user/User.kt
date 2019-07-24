package com.svbackend.mykinotop.dto.user


import com.google.gson.annotations.SerializedName

data class User(
    val profile: Profile,
    val id: Int,
    val email: String,
    val username: String,
    val roles: List<String>,
    val isEmailConfirmed: Boolean
)