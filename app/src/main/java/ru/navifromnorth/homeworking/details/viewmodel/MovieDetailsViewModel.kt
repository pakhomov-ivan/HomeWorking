package ru.navifromnorth.homeworking.details.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.navifromnorth.homeworking.data.MovieDetails
import ru.navifromnorth.homeworking.list.viewmodel.Status
import ru.navifromnorth.homeworking.repository.MoviesRepository
import ru.navifromnorth.homeworking.repository.TMDbMoviesRepository
import java.io.IOException

class MovieDetailsViewModel : ViewModel() {

    //Repository
    private val repository: MoviesRepository = TMDbMoviesRepository.moviesRepository

    //liveData
    private val _mutableSelectedMovie = MutableLiveData<MovieDetails>()
    private val _mutableStatus = MutableLiveData<Status>()

    val selectedMovie: LiveData<MovieDetails> get() = _mutableSelectedMovie
    val status: LiveData<Status> get() = _mutableStatus

    private val networkErrorHandler = CoroutineExceptionHandler { _, exception ->
        Log.d("Network Error", exception.toString())
        when (exception) {
            is IOException -> {
                _mutableStatus.value = Status.Error.NetworkError
                Log.d("Network Error", "problem occurred talking to the server.")
            }
            is RuntimeException -> {
                _mutableStatus.value = Status.Error.InnerError
                Log.d(
                    "Network Error",
                    "an unexpected error occurs creating the request or decoding the response."
                )
            }
            else -> {
                _mutableStatus.value = Status.Error.UnexpectedError
                Log.d("Network Error", "Unexpected error")
            }
        }
    }

    fun getMovieDetails(movieId: Int) {
        _mutableStatus.value = Status.Loading
        val movieDetails =
            viewModelScope.async(Dispatchers.IO) { repository.getMovieDetails(movieId) }
        viewModelScope.launch(networkErrorHandler) {
            _mutableSelectedMovie.value = movieDetails.await()
            _mutableStatus.value = Status.Success
        }
    }
}