package ru.navifromnorth.homeworking

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.data.models.Movie

class PreviewMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    fun onBind(movie: Movie) {
        previewPoster.setImageResource(movie.previewImageId)
        title.setText(movie.titleId)
        rating.rating = movie.rating.toFloat()
        countReviews.text =
            itemView.context.getString(R.string.reviews_text_view, movie.countReviews)
        timing.text =
            itemView.context.getString(R.string.runtime_min_text_view, movie.runtimeInMinutes)
        PG.text = itemView.context.getString(R.string.PG_text_view, movie.PG)

        if (movie.hasLike)
            like.setImageResource(likeEnableImage)
        else
            like.setImageResource(likeDisableImage)

        tags.text = movie.tags.joinToString(separator = ", ",
            transform = { item -> itemView.context.getString(item) })
    }
}