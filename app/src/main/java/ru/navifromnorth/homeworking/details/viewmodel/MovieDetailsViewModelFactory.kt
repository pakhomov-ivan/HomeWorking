package ru.navifromnorth.homeworking.details.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.navifromnorth.homeworking.list.viewmodel.MovieListViewModel
import ru.navifromnorth.homeworking.repository.MoviesRepository
import ru.navifromnorth.homeworking.repository.api.TMDBServiceMapper
import ru.navifromnorth.homeworking.repository.api.TMDbMoviesRepository
import ru.navifromnorth.homeworking.repository.api.TMDbMoviesRepositoryProvider
import ru.navifromnorth.homeworking.repository.database.MoviesDatabase

class MovieDetailsViewModelFactory(
    private val movieID: Long,
    application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {

    private val api: TMDbMoviesRepository by lazy { TMDbMoviesRepositoryProvider.TMDbMoviesRepository }
    private val database: MoviesDatabase by lazy { MoviesDatabase.getInstance(application) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieDetailsViewModel::class.java ->{
            val repo = MoviesRepository(api, database)
            MovieDetailsViewModel(movieID, repo)
        }
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}