package ru.navifromnorth.homeworking.data

data class MovieDetails(
    val id: Long,
    val adult: Int,
    val backdrop: String?,
    val title: String,
    val overview: String?,
    val vote_average: Float,
    val vote_count: Int,
    val like: Boolean,
    val genres: List<Genre>,
    val videos: List<Video>?
)