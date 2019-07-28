package com.svbackend.mykinotop.db

interface UserRepository {
    suspend fun getLoggedInUser(): User?
    suspend fun save(user: User)
    suspend fun getMoviesForMainScreen(): Array<Movie>
}