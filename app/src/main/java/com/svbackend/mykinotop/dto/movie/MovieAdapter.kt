package com.svbackend.mykinotop.dto.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.svbackend.mykinotop.R
import com.svbackend.mykinotop.api.API_HOST
import com.svbackend.mykinotop.internal.DEFAULT_POSTER
import com.svbackend.mykinotop.internal.GlideApp
import kotlinx.android.synthetic.main._movie_cardview.view.*

class MovieAdapter(private val movies: Array<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieCardViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MovieCardViewHolder(val movieCardView: CardView) : RecyclerView.ViewHolder(movieCardView)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieCardViewHolder {
        // create a new view
        val movieCardView = LayoutInflater.from(parent.context)
            .inflate(R.layout._movie_cardview, parent, false) as CardView
        // set the view's size, margins, paddings and layout parameters

        return MovieCardViewHolder(movieCardView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.movieCardView._movie_cardview_TextView_title.text = movies[position].title

        var posterUrl = movies[position].posterUrl?: movies[position].originalPosterUrl

        if (posterUrl.isEmpty()) {
            posterUrl = DEFAULT_POSTER
        } else {
            posterUrl.replace(".jpg", ".320x480.jpg") // 320x480 a bit bigger than needed
            // but its recommended to use this size as it already used in Vue SPA
            // and a lot of posters already cropped to this size + cached on server so its faster to load exactly this size
            posterUrl = "$API_HOST/$posterUrl"
        }

        GlideApp.with(holder.movieCardView.context)
            .load(posterUrl)
            .into(holder.movieCardView._movie_cardview_ImageView_poster)

        holder.movieCardView._movie_cardview_TextView_title.text = movies[position].title
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = movies.size
}