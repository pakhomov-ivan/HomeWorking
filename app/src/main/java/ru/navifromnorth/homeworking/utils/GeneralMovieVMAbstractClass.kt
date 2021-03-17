package ru.navifromnorth.homeworking.utils

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.navifromnorth.homeworking.repository.MoviesRepository

abstract class GeneralMovieVMAbstractClass(private val repository: MoviesRepository) : ViewModel() {

    protected val _mutableLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _mutableLoading.distinctUntilChanged()

    protected val _mutableNetworkError = MutableLiveData(false)
    val isNetworkError: LiveData<Boolean> get() = _mutableNetworkError.distinctUntilChanged()

    fun onLikeClick(movieId: Long, hasLike: Boolean, forceUpdate: Boolean = true) {
        viewModelScope.launch {
            when (hasLike) {
                true -> {
                    repository.updateHasLike(movieId, hasLike)
                    if (forceUpdate) loadMovieDetails(movieId)
                }
                false -> {
                    repository.updateHasLike(movieId, hasLike)
                    repository.dropMovieDetails(movieId)
                }
            }
        }
    }

    fun loadMovieDetails(movieId: Long) {
        _mutableLoading.value = true
        _mutableNetworkError.value = false
        viewModelScope.launch {
            try {
                repository.loadMovieDetails(movieId)
            } catch (exception: Exception) {
                _mutableNetworkError.value = true
            } finally {
                _mutableLoading.value = false
            }
        }
    }
}