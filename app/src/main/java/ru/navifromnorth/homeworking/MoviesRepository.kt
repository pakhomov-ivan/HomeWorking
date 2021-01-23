package ru.navifromnorth.homeworking

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.navifromnorth.homeworking.data.Movie
import ru.navifromnorth.homeworking.data.loadMovies

class MoviesRepository(private val context: Context, private val dispatcher: CoroutineDispatcher) {

    suspend fun getMovies(): List<Movie>? = withContext(dispatcher){ loadMovies(context) }
}