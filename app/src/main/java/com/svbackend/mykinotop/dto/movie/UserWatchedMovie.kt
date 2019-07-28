package com.svbackend.mykinotop.dto.movie


import com.google.gson.annotations.SerializedName

data class UserWatchedMovie(
    val id: Int,
    val vote: String,
    val addedAt: String,
    val watchedAt: Any,
    val isWatched: Boolean
)