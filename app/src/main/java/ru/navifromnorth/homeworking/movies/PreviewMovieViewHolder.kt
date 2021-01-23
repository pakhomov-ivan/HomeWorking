package ru.navifromnorth.homeworking.movies

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.Movie

class PreviewMovieViewHolder(
    itemView: View,
    onViewClick: (Movie?) -> Unit,
    onLikeClick: (Movie?, Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val previewPoster: ImageView = itemView.findViewById(R.id.PreviewImageView)
    private val title: TextView = itemView.findViewById(R.id.FilmTitle)
    private val tags: TextView = itemView.findViewById(R.id.Tags)
    private val rating: RatingBar = itemView.findViewById(R.id.Rating)
    private val countReviews: TextView = itemView.findViewById(R.id.CountReviews)
    private val timing: TextView = itemView.findViewById(R.id.DurationTextView)
    private val PG: TextView = itemView.findViewById(R.id.PG)
    private val like: ImageView = itemView.findViewById(R.id.Like)

    private val likeEnableImage = R.drawable.ic_like_enable
    private val likeDisableImage = R.drawable.ic_like

    private val scope = CoroutineScope(Dispatchers.IO)

    private var currentMovie: Movie? = null

    init {
        itemView.setOnClickListener { onViewClick(currentMovie) }
        like.setOnClickListener { onLikeClick(currentMovie, adapterPosition) }
    }

    fun onBind(movie: Movie) {
        scope.launch {
            val pic = Glide.with(itemView).load(movie.poster)
            launch(Dispatchers.Main) { pic.into(previewPoster) }
        }
        title.text = movie.title
        rating.rating = movie.ratings / 2
        countReviews.text =
            itemView.context.getString(R.string.reviews_text_view, movie.numberOfRatings)
        timing.text = itemView.context.getString(R.string.runtime_min_text_view, movie.runtime)
        PG.text = itemView.context.getString(R.string.PG_text_view, movie.minimumAge)

        if (movie.hasLike) like.setImageResource(likeEnableImage)
        else like.setImageResource(likeDisableImage)

        tags.text = movie.genres.joinToString(separator = ", ",
            transform = { item -> item.name })

        currentMovie = movie
    }
}