package com.svbackend.mykinotop.db

import androidx.lifecycle.LiveData

interface UserRepository {
    suspend fun getLoggedInUser(): LiveData<out User?>
}