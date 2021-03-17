package ru.navifromnorth.homeworking.repository.database.entities.relations_entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import ru.navifromnorth.homeworking.repository.database.MoviesDatabaseContract
import ru.navifromnorth.homeworking.repository.database.entities.GenresEntity
import ru.navifromnorth.homeworking.repository.database.entities.MoviesEntity

//relation many-to-many
@Entity(
    primaryKeys = ["movieId", "genreId"],
    foreignKeys = [
        ForeignKey(
            entity = MoviesEntity::class,
            parentColumns = arrayOf(MoviesDatabaseContract.Movies.Columns.ID),
            childColumns = arrayOf("movieId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenresEntity::class,
            parentColumns = arrayOf(MoviesDatabaseContract.Genres.Columns.ID),
            childColumns = arrayOf("genreId")
        )
    ],
    indices = [Index("movieId"), Index("genreId")],
)
data class MoviesGenresCrossRefEntity(
    val movieId: Long,
    val genreId: Long
)
