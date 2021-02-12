package ru.navifromnorth.homeworking.repository.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesListDTO(@Json(name = "results") val results: List<MovieDTO>)
