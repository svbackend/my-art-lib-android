package com.svbackend.mykinotop.dto.movie


import com.google.gson.annotations.SerializedName

data class Tmdb(
    val id: Int,
    val voteAverage: String,
    val voteCount: Int,
    val isWatched: Boolean
)