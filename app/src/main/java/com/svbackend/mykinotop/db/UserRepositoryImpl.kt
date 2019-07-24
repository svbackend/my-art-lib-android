package com.svbackend.mykinotop.db

import androidx.lifecycle.LiveData
import com.svbackend.mykinotop.api.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userDataSource: UserDataSource
) : UserRepository {
    // todo persist user?
}