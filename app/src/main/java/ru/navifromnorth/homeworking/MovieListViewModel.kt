package ru.navifromnorth.homeworking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.navifromnorth.homeworking.data.Movie

class MovieListViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _mutableMoviesList = MutableLiveData<List<Movie>>()

    val moviesList: LiveData<List<Movie>> get() = _mutableMoviesList

    fun updateMoviesList() {
        viewModelScope.launch {
            _mutableMoviesList.value = moviesRepository.getMovies()
        }
    }

    fun onLikeClick(movieId: Int?) {
        _mutableMoviesList.value?.apply {
            this.first { it.id == movieId }.apply { hasLike = hasLike.not() }
        }
    }
}