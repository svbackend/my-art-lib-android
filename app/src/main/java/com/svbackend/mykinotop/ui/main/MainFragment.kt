package com.svbackend.mykinotop.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.svbackend.mykinotop.R
import com.svbackend.mykinotop.api.ApiService
import com.svbackend.mykinotop.db.UserRepository
import com.svbackend.mykinotop.dto.movie.Movie
import com.svbackend.mykinotop.dto.movie.MovieAdapter
import com.svbackend.mykinotop.ui.ScopedFragment
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MainFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val apiService: ApiService by instance()
    private val userRepository: UserRepository by instance()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private var movies: Array<Movie> = emptyArray()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        loadMovies()
    }

    private fun loadMovies() = launch {
        movies = apiService.getMovies().await().data.toTypedArray()

        viewManager = GridLayoutManager(context, 2)
        viewAdapter = MovieAdapter(movies)

        recyclerView = (view!!.findViewById(R.id.main_RecyclerView_movies) as RecyclerView).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }
}
