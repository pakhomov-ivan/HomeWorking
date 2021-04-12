package ru.navifromnorth.homeworking.data

data class Movie(
        val id: Long,
        val title: String,
        val poster: String?,
        val ratings: Float,
        val numberOfRatings: Int,
        val minimumAge: Int,
        var hasLike: Boolean = false,
        val genres: List<Genre>,
)