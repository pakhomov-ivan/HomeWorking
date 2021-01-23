package ru.navifromnorth.homeworking

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers

class MovieListVMFactory(private val context: Context, private val router: Router?) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieListViewModel::class.java -> MovieListViewModel(
            MoviesRepository(
                context = context,
                dispatcher =  Dispatchers.IO
            ),
            router
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}