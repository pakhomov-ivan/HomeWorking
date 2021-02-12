package ru.navifromnorth.homeworking.repository.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "adult") val adult: Boolean,
    @Json(name = "backdrop_path") val backdrop: String?,
    @Json(name = "genres") val genres: List<GenreDTO>,
    @Json(name = "overview") val overview: String?,
    @Json(name = "runtime") val runtime: Int?,
    @Json(name = "title") val title: String,
    @Json(name = "vote_average") val vote_average: Float,
    @Json(name = "vote_count") val vote_count: Int,
    @Json(name = "video") val video: Boolean
)
