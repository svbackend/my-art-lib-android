package com.svbackend.mykinotop.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM user ORDER BY user.id ASC LIMIT 1")
    fun getLoggedInUser(): User?

    @Query("SELECT * FROM movie WHERE movie.screen = :screen")
    fun getMoviesForScreen(screen: String): Array<Movie>
}