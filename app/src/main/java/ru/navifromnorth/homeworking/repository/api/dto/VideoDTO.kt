package ru.navifromnorth.homeworking.repository.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoDTO(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "key") val key: String,
    @Json(name = "site") val site: String,
    @Json(name = "type") val type: String
)