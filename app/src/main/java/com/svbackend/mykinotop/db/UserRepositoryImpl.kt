package com.svbackend.mykinotop.db

import androidx.lifecycle.LiveData
import com.svbackend.mykinotop.api.UserDataSource

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getCurrentUser(): LiveData<User> {

    }
}