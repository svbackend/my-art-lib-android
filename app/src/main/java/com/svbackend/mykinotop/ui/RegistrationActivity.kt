package com.svbackend.mykinotop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.svbackend.mykinotop.R
import com.svbackend.mykinotop.ui.registration.RegistrationFragment

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RegistrationFragment.newInstance())
                .commitNow()
        }
    }

}
