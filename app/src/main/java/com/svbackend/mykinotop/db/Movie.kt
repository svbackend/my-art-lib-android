package com.svbackend.mykinotop.db

import androidx.room.Entity
import androidx.room.PrimaryKey

const val SCREEN_MAIN = "main"
const val SCREEN_LIBRARY = "library"
const val SCREEN_RECOMMENDATIONS = "recommendations"

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val year: Int,
    val poster: String,
    val isWatched: Boolean,
    val screen: String,
    val userVote: String?,
    val averageVote: String
)