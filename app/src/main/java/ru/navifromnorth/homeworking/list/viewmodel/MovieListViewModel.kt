package ru.navifromnorth.homeworking.list.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.navifromnorth.homeworking.data.Movie
import ru.navifromnorth.homeworking.repository.MoviesRepository
import ru.navifromnorth.homeworking.repository.TMDbMoviesRepository
import java.io.IOException

class MovieListViewModel : ViewModel() {

    private val repository: MoviesRepository = TMDbMoviesRepository.moviesRepository

    var pageNum: Int = 1
        private set

    private val _mutableMoviesList =
        MutableLiveData(emptyList<Movie>().toMutableList())
    private val _mutableStatus = MutableLiveData<Status>()

    val moviesList: LiveData<MutableList<Movie>> get() = _mutableMoviesList
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

    fun loadNextPage() {
        loadPageNum(pageNum++)
    }

    fun loadPageNum(page: Int) {
        _mutableStatus.value = Status.Loading
        val movieList = viewModelScope.async(Dispatchers.IO) { repository.getPopularMovies(page) }
        viewModelScope.launch(networkErrorHandler) {
            _mutableMoviesList.value?.addAll(movieList.await())
            _mutableStatus.value = Status.Success
        }
    }

    fun onLikeClick(movieId: Int?) {
        _mutableMoviesList.value?.apply {
            this.first { it.id == movieId }.apply { hasLike = hasLike.not() }
        }
    }
}

sealed class Status {
    object Loading : Status()
    object Success : Status()
    sealed class Error : Status() {
        object NetworkError : Error()
        object InnerError : Error()
        object UnexpectedError : Error()
    }
}