package com.svbackend.mykinotop.api

import androidx.lifecycle.LiveData
import com.svbackend.mykinotop.db.User
import com.svbackend.mykinotop.dto.user.User as UserDTO

interface UserDataSource {
    val currentUser: LiveData<UserDTO>

    suspend fun fetchCurrentUser(
        id: Int
    )
}