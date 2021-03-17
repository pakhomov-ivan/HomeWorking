package ru.navifromnorth.homeworking.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.navifromnorth.homeworking.data.MovieDetails
import ru.navifromnorth.homeworking.repository.api.TMDbMoviesRepository
import ru.navifromnorth.homeworking.repository.database.MoviesDatabase
import ru.navifromnorth.homeworking.utils.Result

class MoviesRepository(
    private val webservice: TMDbMoviesRepository,
    private val localStorage: MoviesDatabase,
) {

    val popularMovies get() = localStorage.movies.getAllMovies()

    suspend fun refreshGenres() = withContext(Dispatchers.IO) {
        when (val genres = webservice.getGenres()) {
            is Result.Success -> localStorage.genres.insertAll(
                genres.data.map { it.toEntityModel() }
            )
            is Result.Error -> throw genres.exception
        }
    }

    suspend fun refreshPopularMovies(page: Int) = withContext(Dispatchers.IO) {
        when (val moviesList = webservice.getPopularMovies(page)) {
            is Result.Success -> {
                localStorage.movies.deleteAllNotLikedMoviesWithGenres()
                localStorage.videos.dropAllMovieVideos()
                localStorage.movies.insertAllMovieWithGenres(moviesList.data)
            }
            is Result.Error -> throw moviesList.exception
        }
    }

    suspend fun loadPopularMovies(page: Int) = withContext(Dispatchers.IO) {
        when (val movies = webservice.getPopularMovies(page)) {
            is Result.Success -> {
                localStorage.movies.insertAllMovieWithGenres(movies.data)
            }
            is Result.Error -> throw movies.exception
        }
    }

    suspend fun updateHasLike(movieId: Long, hasLike: Boolean) = withContext(Dispatchers.IO) {
        localStorage.movies.setLike(movieId, hasLike)
    }

    suspend fun loadMovieDetails(movieId: Long): MovieDetails = withContext(Dispatchers.IO) {
        when (val videosList = webservice.getMovieVideos(movieId)) {
            is Result.Success -> localStorage.videos.insertAllVideos(videosList.data)
            is Result.Error -> {}//throw videosList.exception
        }
        return@withContext localStorage.movies.getMovieDetails(movieId).asDomainMovieDetails()
    }

    suspend fun dropMovieDetails(movieId: Long) = withContext(Dispatchers.IO) {
        localStorage.videos.dropMovieVideos(movieId)
    }
}