package ru.navifromnorth.homeworking.repository.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesListDTO(
    @Json(name = "results") val results: List<MovieDTO>,
    @Json(name = "page") val page: Long
)

@JsonClass(generateAdapter = true)
data class MovieDTO(
    @Json(name = "id") val id: Long,
    @Json(name = "original_title") val title: String,
    @Json(name = "poster_path") val posterUrl: String?,
    @Json(name = "backdrop_path") val backdropUrl: String?,
    @Json(name = "genre_ids") val genres: List<Long>,
    @Json(name = "vote_count") val countReviews: Int,
    @Json(name = "vote_average") val rating: Float,
    @Json(name = "adult") val isAdult: Boolean,
    @Json(name = "overview") val overview: String
)
