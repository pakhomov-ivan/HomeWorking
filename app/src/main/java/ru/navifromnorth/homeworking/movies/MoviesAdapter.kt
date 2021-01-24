package ru.navifromnorth.homeworking.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.Movie

class MoviesAdapter(
    private val onMovieClick: (movie: Movie?) -> Unit,
    private val onLikeClick: (movieId: Int?) -> Unit
) : RecyclerView.Adapter<PreviewMovieViewHolder>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewMovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false)

        val onViewClick = { movie: Movie? -> onMovieClick(movie) }
        val onLikeClick = { movie: Movie?, i: Int ->
            onLikeClick(movie?.id)
            notifyItemChanged(i)
        }
        return PreviewMovieViewHolder(view, onViewClick = onViewClick, onLikeClick = onLikeClick)
    }

    override fun onBindViewHolder(holder: PreviewMovieViewHolder, position: Int) {
        holder.onBind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}
