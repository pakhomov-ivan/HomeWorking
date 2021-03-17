package ru.navifromnorth.homeworking.repository.database.dao

import androidx.paging.DataSource
import androidx.room.*
import ru.navifromnorth.homeworking.repository.database.entities.MoviesEntity
import ru.navifromnorth.homeworking.repository.database.entities.relations_entity.MoviesDetailsRelation
import ru.navifromnorth.homeworking.repository.database.entities.relations_entity.MovieWithGenres
import ru.navifromnorth.homeworking.repository.database.entities.relations_entity.MoviesGenresCrossRefEntity

@Dao
interface MoviesDao {

    @Transaction
    @Query("Select * from Movies where _id = :movieId")
    fun getMovieDetails(movieId: Long): MoviesDetailsRelation

    @Transaction
    @Query("Select * from Movies order by pageNum")
    fun getAllMovies(): DataSource.Factory<Int, MovieWithGenres>//LiveData<List<MovieWithGenres>>

    @Transaction
    fun insertAllMovieWithGenres(moviesList: List<MovieWithGenres>) {
        moviesList.forEach { insertMovieWithGenres(it) }
    }

    @Transaction
    fun insertMovieWithGenres(movie: MovieWithGenres) {
        insertMovie(movie.movie)
        movie.genres.forEach {
            insertMoviesGenresCrossRef(
                MoviesGenresCrossRefEntity(movie.movie.id, it.id)
            )
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = MoviesEntity::class)
    fun insertMovie(movie: MoviesEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = MoviesGenresCrossRefEntity::class)
    fun insertMoviesGenresCrossRef(refs: MoviesGenresCrossRefEntity)

    @Query("Delete from Movies where hasLike = 0")
    fun deleteAllNotLikedMoviesWithGenres()

    @Query("Update Movies set hasLike = :isLike where _id = :movieID")
    fun setLike(movieID: Long, isLike: Boolean)
}