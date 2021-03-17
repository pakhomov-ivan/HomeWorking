package ru.navifromnorth.homeworking.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
        val id: Long,
        val title: String,
        val poster: String?,
        val ratings: Float,
        val numberOfRatings: Int,
        val minimumAge: Int,
        var hasLike: Boolean = false,
        val genres: List<Genre>,
) : Parcelable