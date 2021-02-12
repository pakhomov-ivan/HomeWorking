package ru.navifromnorth.homeworking.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.Movie

class MoviesAdapter(
    private val onMovieClick: (movieId: Int) -> Unit,
    private val onLikeClick: (movieId: Int) -> Unit,
    private val onListEnded: () -> Unit
) : RecyclerView.Adapter<PreviewMovieViewHolder>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewMovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false)

        val onViewClick = { movieId: Int -> onMovieClick(movieId) }
        val onLikeClick = { movieId: Int, i: Int ->
            onLikeClick(movieId)
            notifyItemChanged(i)
        }
        return PreviewMovieViewHolder(view, onViewClick = onViewClick, onLikeClick = onLikeClick)
    }

    override fun onBindViewHolder(holder: PreviewMovieViewHolder, position: Int) {
        holder.onBind(movies[position])
        if (position >= movies.size - 2)
            onListEnded()
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<Movie>) {
        val diff = DiffUtil.calculateDiff(
            MoviesListDiffUtilCallback(movies, newMovies)
        )
        diff.dispatchUpdatesTo(this)
        movies = newMovies
    }
}

class MoviesListDiffUtilCallback(
    private val oldList: List<Movie>,
    private val newList: List<Movie>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        = oldList[oldItemPosition] == newList[newItemPosition]

}
