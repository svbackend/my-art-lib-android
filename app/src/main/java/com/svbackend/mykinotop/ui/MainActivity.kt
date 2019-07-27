package com.svbackend.mykinotop.ui

import android.content.Intent
import android.os.Bundle
import com.svbackend.mykinotop.R
import com.svbackend.mykinotop.db.UserRepository
import com.svbackend.mykinotop.ui.moviessearch.MoviesSearchFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlinx.coroutines.launch


class MainActivity : ScopedActivity(), KodeinAware {
    override val kodein by closestKodein()
    private val userRepository: UserRepository by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_search_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MoviesSearchFragment.newInstance())
                .commitNow()
        }

        launch {
            if (userRepository.getLoggedInUser() == null) {
                switchToLoginActivity()
            }
        }
    }

    private fun switchToLoginActivity() {
        val loginActivityIntent = Intent(this@MainActivity, LoginActivity::class.java)
        loginActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(loginActivityIntent)
    }
}