package ru.navifromnorth.homeworking.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.navifromnorth.homeworking.data.Genre
import ru.navifromnorth.homeworking.repository.database.MoviesDatabaseContract

@Entity(tableName = MoviesDatabaseContract.Genres.TABLE_NAME)
data class GenresEntity(
        @PrimaryKey
        @ColumnInfo(name = MoviesDatabaseContract.Genres.Columns.ID)
        val id: Long,

        @ColumnInfo(name = MoviesDatabaseContract.Genres.Columns.NAME)
        val name: String = ""
) {
    fun asDomainGenre() = Genre(this.id, this.name)
}
