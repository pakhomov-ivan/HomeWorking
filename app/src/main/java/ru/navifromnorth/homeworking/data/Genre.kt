package ru.navifromnorth.homeworking.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.navifromnorth.homeworking.repository.database.entities.GenresEntity

@Parcelize
data class Genre(val id: Long, val name: String) : Parcelable {
    fun toEntityModel() = GenresEntity(id = id, name = name)
}