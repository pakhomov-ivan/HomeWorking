package ru.navifromnorth.homeworking.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String?,
//    val backdrop: String?,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
//    val runtime: Int?,
    var hasLike: Boolean = false,
    val genres: List<Genre>,
//    val genres: List<Int>,
) : Parcelable