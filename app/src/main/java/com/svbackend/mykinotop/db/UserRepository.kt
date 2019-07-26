package com.svbackend.mykinotop.db

import androidx.lifecycle.LiveData

interface UserRepository {
    suspend fun getLoggedInUser(): User?
    suspend fun save(user: User)
}