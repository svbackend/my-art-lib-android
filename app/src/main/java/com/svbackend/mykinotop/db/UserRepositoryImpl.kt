package com.svbackend.mykinotop.db

import androidx.lifecycle.LiveData
import com.svbackend.mykinotop.api.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun getLoggedInUser(): User? {
        return withContext(Dispatchers.IO) {
            userDao.getLoggedInUser()
        }
    }

    override suspend fun save(user: User) {
        return withContext(Dispatchers.IO) {
            userDao.insert(user)
        }
    }
}