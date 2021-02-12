package ru.navifromnorth.homeworking.list

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.Movie

class PreviewMovieViewHolder(
    itemView: View,
    onViewClick: (Int) -> Unit,
    onLikeClick: (Int, Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val previewPoster: ImageView = itemView.findViewById(R.id.PreviewImageView)
    private val title: TextView = itemView.findViewById(R.id.FilmTitle)
    private val tags: TextView = itemView.findViewById(R.id.Tags)
    private val rating: RatingBar = itemView.findViewById(R.id.Rating)
    private val countReviews: TextView = itemView.findViewById(R.id.CountReviews)
    private val PG: TextView = itemView.findViewById(R.id.PG)
    private val like: ImageView = itemView.findViewById(R.id.Like)

    private val likeEnableImage = R.drawable.ic_like_enable
    private val likeDisableImage = R.drawable.ic_like

    private var currentMovieId: Int? = null

    init {
        itemView.setOnClickListener { currentMovieId?.let { it1 -> onViewClick(it1) } }
        like.setOnClickListener { currentMovieId?.let { it1 -> onLikeClick(it1, adapterPosition) } }
    }

    fun onBind(movie: Movie) {
        previewPoster.load(movie.poster)
        title.text = movie.title
        rating.rating = movie.ratings / 2
        countReviews.text =
            itemView.context.getString(R.string.reviews_text_view, movie.numberOfRatings)
        PG.text = itemView.context.getString(R.string.PG_text_view, movie.minimumAge)

        if (movie.hasLike) like.setImageResource(likeEnableImage)
        else like.setImageResource(likeDisableImage)

        tags.text = movie.genres.joinToString(separator = ", ", transform = { item -> item.name })

        currentMovieId = movie.id
    }
}