package com.svbackend.mykinotop.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.svbackend.mykinotop.dto.user.User

class UserDataSourceImpl(
    private val apiService: ApiService
) : UserDataSource {

    private val _currentUser = MutableLiveData<User>()
    override val currentUser: LiveData<User>
        get() = _currentUser

    override suspend fun fetchCurrentUser(id: Int) {
        // todo
    }
}