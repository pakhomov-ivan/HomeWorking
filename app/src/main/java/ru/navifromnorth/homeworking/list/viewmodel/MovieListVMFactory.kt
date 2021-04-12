package ru.navifromnorth.homeworking.list.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.navifromnorth.homeworking.repository.MoviesRepository
import ru.navifromnorth.homeworking.repository.api.*
import ru.navifromnorth.homeworking.repository.database.MoviesDatabase

class MovieListVMFactory(application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {

    private val webservice: TMDBServiceAPI = RetrofitMoviesCreator
        .setSettings(
            application
                .getSharedPreferences(RetrofitMoviesCreator.NETWORK_SETTINGS, Context.MODE_PRIVATE)
        ).getInstance().create(TMDBServiceAPI::class.java)
    private val database: MoviesDatabase by lazy { MoviesDatabase.getInstance(application) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieListViewModel::class.java -> {
            val repo = MoviesRepository(
                TMDbMoviesRepository(webservice, TMDBServiceMapper),
                database
            )
            MovieListViewModel(repo)
        }
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}