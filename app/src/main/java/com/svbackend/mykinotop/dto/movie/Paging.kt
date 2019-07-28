package com.svbackend.mykinotop.dto.movie


import com.google.gson.annotations.SerializedName

data class Paging(
    val total: Int,
    val offset: Int,
    val limit: Int
)