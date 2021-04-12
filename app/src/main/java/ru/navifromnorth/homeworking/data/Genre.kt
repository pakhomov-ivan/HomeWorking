package ru.navifromnorth.homeworking.data

import ru.navifromnorth.homeworking.repository.database.entities.GenresEntity

data class Genre(val id: Long, val name: String){
    fun toEntityModel() = GenresEntity(id = id, name = name)
}