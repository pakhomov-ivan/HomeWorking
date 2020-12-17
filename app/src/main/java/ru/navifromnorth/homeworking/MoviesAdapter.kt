package ru.navifromnorth.homeworking

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.data.models.Movie

class MoviesAdapter(
    private val clickListener: ClickListener,
    private val onLikeCallback: ClickOnLikeCallback,
    context: Context
) : RecyclerView.Adapter<PreviewMovieViewHolder>() {

    private var movies = listOf<Movie>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewMovieViewHolder {
        return PreviewMovieViewHolder(
            inflater.inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PreviewMovieViewHolder, position: Int) {
        holder.onBind(movies[position])
        holder.itemView.setOnClickListener {
            clickListener.onMovieClick(movies[position])
        }

        holder.itemView.findViewById<ImageView>(R.id.Like).setOnClickListener {
            movies[position].hasLike = !movies[position].hasLike
            onLikeCallback.onLikeClick(movies)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}

interface ClickListener {
    fun onMovieClick(movie: Movie)
}

interface ClickOnLikeCallback {
    fun onLikeClick(_movies: List<Movie>)
}
