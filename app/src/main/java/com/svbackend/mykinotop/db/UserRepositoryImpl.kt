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

    init {
        userDataSource.currentUser.observeForever {newUser ->
            persistFetchedUser(newUser)
        }
    }

    override suspend fun getCurrentUser(id: Int): LiveData<User> {
        TODO()
    }

    private fun persistFetchedUser(user: User) {
        GlobalScope.launch(Dispatchers.IO) {
            userDao.insert(user)
        }
    }
}