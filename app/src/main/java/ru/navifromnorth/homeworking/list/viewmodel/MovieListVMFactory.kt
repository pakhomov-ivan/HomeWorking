package ru.navifromnorth.homeworking.list.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.navifromnorth.homeworking.repository.MoviesRepository
import ru.navifromnorth.homeworking.repository.api.TMDbMoviesRepository
import ru.navifromnorth.homeworking.repository.api.TMDbMoviesRepositoryProvider
import ru.navifromnorth.homeworking.repository.database.MoviesDatabase

class MovieListVMFactory(application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {

    private val api: TMDbMoviesRepository by lazy { TMDbMoviesRepositoryProvider.TMDbMoviesRepository }
    private val database: MoviesDatabase by lazy { MoviesDatabase.getInstance(application) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieListViewModel::class.java -> {
            val repo = MoviesRepository(api, database)
            MovieListViewModel(repo)
        }
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}