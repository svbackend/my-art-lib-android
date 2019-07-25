package com.svbackend.mykinotop.ui.moviessearch

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.svbackend.mykinotop.R

class MoviesSearchFragment : Fragment() {

    companion object {
        fun newInstance() = MoviesSearchFragment()
    }

    private lateinit var viewModel: MoviesSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movies_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MoviesSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
