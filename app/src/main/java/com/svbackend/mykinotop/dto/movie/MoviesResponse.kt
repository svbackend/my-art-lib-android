package com.svbackend.mykinotop.dto.movie


import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val `data`: List<Movie>,
    val paging: Paging
)