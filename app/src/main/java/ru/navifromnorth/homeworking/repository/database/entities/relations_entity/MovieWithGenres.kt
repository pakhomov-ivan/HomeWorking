package ru.navifromnorth.homeworking.repository.database.entities.relations_entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.navifromnorth.homeworking.data.Movie
import ru.navifromnorth.homeworking.data.MovieDetails
import ru.navifromnorth.homeworking.repository.database.MoviesDatabaseContract
import ru.navifromnorth.homeworking.repository.database.entities.GenresEntity
import ru.navifromnorth.homeworking.repository.database.entities.MoviesEntity

data class MovieWithGenres(
    @Embedded val movie: MoviesEntity,
    @Relation(
        parentColumn = MoviesDatabaseContract.Movies.Columns.ID,
        entityColumn = MoviesDatabaseContract.Genres.Columns.ID,
        associateBy = Junction(
            value = MoviesGenresCrossRefEntity::class,
            parentColumn = "movieId",
            entityColumn = "genreId"
        )
    )
    val genres: List<GenresEntity>
) {
    fun asDomainMoviePreview() = Movie(
        id = movie.id,
        title = movie.title,
        poster = movie.poster,
        ratings = movie.vote_average,
        numberOfRatings = movie.vote_count,
        minimumAge = movie.adult,
        hasLike = movie.hasLike,
        genres = genres.map { it.asDomainGenre() }
    )
}
