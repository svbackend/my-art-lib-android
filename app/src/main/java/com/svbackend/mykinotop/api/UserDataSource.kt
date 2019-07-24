package com.svbackend.mykinotop.api

import androidx.lifecycle.LiveData
import com.svbackend.mykinotop.dto.user.User

interface UserDataSource {
    val currentUser: LiveData<User>

    suspend fun fetchCurrentUser(
        id: Int
    )
}