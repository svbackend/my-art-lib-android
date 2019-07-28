package com.svbackend.mykinotop.ui

import android.content.Intent
import android.os.Bundle
import com.svbackend.mykinotop.R
import com.svbackend.mykinotop.db.UserRepository
import com.svbackend.mykinotop.ui.main.MainFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlinx.coroutines.launch


class MainActivity : ScopedActivity(), KodeinAware {
    override val kodein by closestKodein()
    private val userRepository: UserRepository by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launch {
            if (userRepository.getLoggedInUser() == null) {
                switchToLoginActivity()
            }
        }

        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun switchToLoginActivity() {
        val loginActivityIntent = Intent(this, LoginActivity::class.java)
        loginActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(loginActivityIntent)
    }
}