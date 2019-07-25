package com.svbackend.mykinotop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.svbackend.mykinotop.R
import com.svbackend.mykinotop.ui.moviessearch.MoviesSearchFragment

class MoviesSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_search_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MoviesSearchFragment.newInstance())
                .commitNow()
        }
    }

}
