package com.svbackend.mykinotop.dto.movie

data class Movie(
    val id: Int,
    val originalTitle: String,
    val originalPosterUrl: String,
    val releaseDate: String,
    val locale: String,
    val title: String,
    val isWatched: Boolean,
    val tmdb: Tmdb,
    //val userWatchedMovie: UserWatchedMovie,
    val posterUrl: String?,
    val imdbId: String
)