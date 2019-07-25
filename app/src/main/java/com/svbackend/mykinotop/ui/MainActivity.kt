package com.svbackend.mykinotop.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.svbackend.mykinotop.R
import com.svbackend.mykinotop.db.UserRepository
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlinx.coroutines.launch


class MainActivity : ScopedActivity(), KodeinAware {
    override val kodein by closestKodein()
    private val userRepository: UserRepository by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkIsLoggedIn()
    }

    private fun checkIsLoggedIn() = launch {
        val user = userRepository.getLoggedInUser()

        if (user.value == null) {
            setContentView(R.layout.activity_main)
            setSupportActionBar(toolbar)
            return@launch
        }

        val moviesSearchIntent = Intent(this@MainActivity, MoviesSearchActivity::class.java)

        startActivity(moviesSearchIntent)
        finish()
    }

    fun goToLogin(v: View) {
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
    }

    fun goToRegistration(v: View) {
        val registrationIntent = Intent(this, RegistrationActivity::class.java)
        startActivity(registrationIntent)
    }
}