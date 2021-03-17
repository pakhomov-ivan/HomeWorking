package ru.navifromnorth.homeworking.repository.database.entities.relations_entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.navifromnorth.homeworking.data.MovieDetails
import ru.navifromnorth.homeworking.repository.database.MoviesDatabaseContract
import ru.navifromnorth.homeworking.repository.database.entities.GenresEntity
import ru.navifromnorth.homeworking.repository.database.entities.MoviesEntity
import ru.navifromnorth.homeworking.repository.database.entities.VideosEntity

data class MoviesDetailsRelation(
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
    val genres: List<GenresEntity>,

    @Relation(
        parentColumn = MoviesDatabaseContract.Movies.Columns.ID,
        entityColumn = MoviesDatabaseContract.Videos.Columns.MOVIE_ID
    )
    val videos: List<VideosEntity>?
){
    fun asDomainMovieDetails() = MovieDetails(
        id = movie.id,
        overview = movie.overview,
        backdrop = movie.backdrop,
        title = movie.title,
        vote_average = movie.vote_average,
        vote_count = movie.vote_count,
        adult = movie.adult,
        like = movie.hasLike,
        genres = genres.map { it.asDomainGenre() },
        videos = videos?.map { it.asDomain() }
    )
}
