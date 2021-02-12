package ru.navifromnorth.homeworking.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.navifromnorth.homeworking.data.Genre
import ru.navifromnorth.homeworking.data.Movie
import ru.navifromnorth.homeworking.data.MovieDetails
import ru.navifromnorth.homeworking.repository.api.TMDBServiceAPI
import ru.navifromnorth.homeworking.repository.api.dto.MovieDTO
import ru.navifromnorth.homeworking.repository.api.dto.MovieDetailsDTO

class MoviesRepository(
    private val tmdbServiceApi: TMDBServiceAPI,
    private val tmdbServiceMapper: TMDBServiceMapper
) {

    val genresList: List<Genre> by lazy {
        runBlocking { getGenres() }
    }

    suspend fun getPopularMovies(pageNum: Int): List<Movie> {
        val result = tmdbServiceApi.getPopularMovies(X_API_KEY, pageNum)
        return tmdbServiceMapper.map(result, genresList)
    }

    suspend fun getMovieDetails(id: Int): MovieDetails {
        val movie: MovieDetailsDTO = tmdbServiceApi.getMovieDetails(id, X_API_KEY)
        Log.d("isVideo", movie.toString())
        //val videoList = if (movie.video) tmdbServiceApi.getViedos(id, X_API_KEY) else null
        val videoList = tmdbServiceApi.getViedos(id, X_API_KEY)
        return tmdbServiceMapper.map(movie, videoList)
    }

    suspend fun getGenres(): List<Genre> {
        val response = withContext(Dispatchers.IO) { tmdbServiceApi.getGenres(X_API_KEY) }
        return tmdbServiceMapper.map(response)
    }

    companion object {
        private const val X_API_KEY = "5153ba26a4f6522b975457f7929734c9"
    }
}