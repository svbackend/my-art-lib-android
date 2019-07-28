package com.svbackend.mykinotop.dto.registration

import com.google.gson.annotations.SerializedName


data class RegistrationRequest(
    @SerializedName("registration")
    val registrationData: RegistrationData
)