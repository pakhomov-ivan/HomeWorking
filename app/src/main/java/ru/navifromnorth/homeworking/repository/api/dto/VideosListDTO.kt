package ru.navifromnorth.homeworking.repository.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideosListDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "results") val results: List<VideoDTO>
)
