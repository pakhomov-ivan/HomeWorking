package ru.navifromnorth.homeworking.data

data class MovieDetails(
    val id: Int,
    val adult: Boolean,
    val backdrop: String?,
    val genres: List<Genre>,
    val title: String,
    val overview: String?,
    val runtime: Int?,
    val vote_average: Float,
    val vote_count: Int,
    val video: List<Video>?
)