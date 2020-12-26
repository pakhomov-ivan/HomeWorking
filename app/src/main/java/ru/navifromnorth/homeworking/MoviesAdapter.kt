package ru.navifromnorth.homeworking

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.data.models.Movie

class MoviesAdapter(private val clickListener: ClickListener, context: Context) :
    RecyclerView.Adapter<PreviewMovieViewHolder>() {

    private var movies: List<Movie> = listOf()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewMovieViewHolder {
        return PreviewMovieViewHolder(
            inflater.inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PreviewMovieViewHolder, position: Int) {
        holder.onBind(movies[position])
//        holder.itemView.setOnClickListener {
//            clickListener.onMovieClick(movies[position])
//        }

        holder.itemView.findViewById<ImageView>(R.id.Like).setOnClickListener {
            movies[position].hasLike = !movies[position].hasLike
            clickListener.onLikeClick(movies[position])
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<Movie>?) {
        movies = newMovies ?: listOf()
        notifyDataSetChanged()
    }
}

interface ClickListener {
    fun onMovieClick(movie: Movie)

    fun onLikeClick(movie: Movie)
}
