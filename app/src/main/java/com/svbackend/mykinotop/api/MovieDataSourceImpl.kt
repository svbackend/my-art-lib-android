package com.svbackend.mykinotop.api

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.svbackend.mykinotop.dto.movie.Movie
import com.svbackend.mykinotop.dto.movie.MoviesResponse
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class MovieDataSourceImpl(
    private val apiService: ApiService
) : MovieDataSource, PageKeyedDataSource<String, Movie>() {

    private fun nextOffset(currentOffset: Int, limit: Int = 20): String {
        return (currentOffset + limit).toString()
    }

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Movie>) {
        val request = apiService.getMovies()

        try {
            val response = request.execute()
            val data = response.body()!!
            val movies = data.data
            callback.onResult(movies, 0.toString(), nextOffset(data.paging.offset))
        } catch (ioException: IOException) {
            Log.e("PAGING", ioException.message ?: "IO Exception (Initial load of movies in paging)")
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Movie>) {
        return apiService.getMovies(params.key.toInt()).enqueue(object : retrofit2.Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                // todo show err msg
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (!response.isSuccessful) {
                    // todo err handling
                    return
                }

                val data = response.body()!!
                val movies = data.data
                val newOffset = data.paging.offset + data.paging.limit
                callback.onResult(movies, newOffset.toString())
            }
        })
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Movie>) {
        // do I need it?
    }
}