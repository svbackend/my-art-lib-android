package com.svbackend.mykinotop.db

import kotlinx.coroutines.Dispatchers
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

    override suspend fun getMoviesForMainScreen(): Array<Movie> {
        return withContext(Dispatchers.IO) {
            userDao.getMoviesForScreen(SCREEN_MAIN)
        }
    }
}