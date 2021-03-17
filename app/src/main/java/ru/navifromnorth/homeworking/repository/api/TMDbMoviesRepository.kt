package ru.navifromnorth.homeworking.repository.api

import ru.navifromnorth.homeworking.data.Genre
import ru.navifromnorth.homeworking.data.MovieDetails
import ru.navifromnorth.homeworking.data.Video
import ru.navifromnorth.homeworking.repository.database.entities.VideosEntity
import ru.navifromnorth.homeworking.repository.database.entities.relations_entity.MovieWithGenres
import ru.navifromnorth.homeworking.utils.Result

class TMDbMoviesRepository(
    private val tmdbServiceApi: TMDBServiceAPI,
    private val tmdbServiceMapper: TMDBServiceMapper
) {

    suspend fun getGenres(): Result<List<Genre>> = apiCall {
        tmdbServiceMapper.map(
            tmdbServiceApi.getGenres(X_API_KEY)
        )
    }

    suspend fun getPopularMovies(pageNum: Int): Result<List<MovieWithGenres>> = apiCall {
        tmdbServiceMapper.mapEntity(
            tmdbServiceApi.getPopularMovies(X_API_KEY, pageNum)
        )
    }

//    suspend fun getMovieDetails(id: Long): Result<MovieDetails> = apiCall {
//        tmdbServiceMapper.map(
//            tmdbServiceApi.getMovieDetails(id, X_API_KEY)
//        )
//    }

    suspend fun getMovieVideos(movieId: Long): Result<List<VideosEntity>> = apiCall {
        tmdbServiceMapper.map(
            tmdbServiceApi.getMovieVideos(movieId, X_API_KEY)
        )
    }

    private suspend fun <T> apiCall(call: suspend () -> T): Result<T> =
        try {
            Result.Success(call())
        } catch (exception: Exception) {
            Result.Error(exception)
        }

    companion object {
        private const val X_API_KEY = "5153ba26a4f6522b975457f7929734c9"
    }
}