package ru.navifromnorth.homeworking.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import ru.navifromnorth.homeworking.data.MovieDetails
import ru.navifromnorth.homeworking.repository.MoviesRepository
import ru.navifromnorth.homeworking.utils.GeneralMovieVMAbstractClass

class MovieDetailsViewModel(
    private val movieId: Long,
    private val repository: MoviesRepository
) : GeneralMovieVMAbstractClass(repository) {

    val selectedMovie: LiveData<MovieDetails>
        get() = liveData {
            _mutableNetworkError.value = false
            _mutableLoading.value = true
            try {
                val movieDetails = repository.loadMovieDetails(movieId)
                emit(movieDetails)
            } catch (exception: Exception) {
                _mutableNetworkError.value = true
            } finally {
                _mutableLoading.value = false
            }
        }
}