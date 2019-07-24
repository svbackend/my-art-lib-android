package com.svbackend.mykinotop.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val username: String,
    val apiToken: String
)