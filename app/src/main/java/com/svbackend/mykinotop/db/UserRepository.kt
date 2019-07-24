package com.svbackend.mykinotop.db

import androidx.lifecycle.LiveData

interface UserRepository {
    suspend fun getCurrentUser(): LiveData<User>
}