package ru.navifromnorth.homeworking.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.Movie

class MoviesAdapter(
    private val onMovieClick: (movieId: Long) -> Unit,
    private val onLikeClick: (movieId: Long, hasLike: Boolean) -> Unit
) : PagedListAdapter<Movie, PreviewMovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewMovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false)

        return PreviewMovieViewHolder(
            view,
            onViewClick = { movieId: Long -> onMovieClick(movieId) },
            onLikeClick = { movieId: Long, i: Int ->
                getItem(i)?.let { onLikeClick(movieId, it.hasLike.not()) }
            }
        )
    }

    override fun onBindViewHolder(holder: PreviewMovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.onBind(movie)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}