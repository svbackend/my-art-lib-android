package com.svbackend.mykinotop.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val username = ObservableField("user")
    val password = ObservableField("test")

    fun login() {
        username.set("clicked!!!")
    }
}
