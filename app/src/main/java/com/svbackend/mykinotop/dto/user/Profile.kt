package com.svbackend.mykinotop.dto.user


import com.google.gson.annotations.SerializedName

data class Profile(
    val about: String,
    val contacts: List<Any>,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("birth_date")
    val birthDate: String,
    @SerializedName("public_email")
    val publicEmail: String,
    @SerializedName("country_code")
    val countryCode: String
)