package ru.navifromnorth.homeworking.list.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import kotlinx.coroutines.launch
import ru.navifromnorth.homeworking.data.Movie
import ru.navifromnorth.homeworking.repository.MoviesRepository
import ru.navifromnorth.homeworking.utils.GeneralMovieVMAbstractClass
import java.io.IOException

class MovieListViewModel(private val repository: MoviesRepository) :
    GeneralMovieVMAbstractClass(repository) {

    private val boundaryCallback = object : PagedList.BoundaryCallback<Movie>() {
        override fun onItemAtEndLoaded(itemAtEnd: Movie) {
            super.onItemAtEndLoaded(itemAtEnd)
            loadNextPage()
        }
    }

    var pageNum: Int = 1
        private set

    val moviesList: LiveData<PagedList<Movie>> = repository.popularMovies.map {
        it.asDomainMoviePreview()
    }.toLiveData(pageSize = 20, boundaryCallback = boundaryCallback)

    init {
        refreshGenres()
        refreshMoviesList()
        //todo: add refresh only liked movies
    }

    private fun refreshGenres() {
        _mutableLoading.value = true
        _mutableNetworkError.value = false
        viewModelScope.launch {
            try {
                repository.refreshGenres()
            } catch (exception: IOException) {
                _mutableNetworkError.value = true
                Log.e("${this::class.simpleName} refreshGenres", "Catch exception: $exception")
            } finally {
                _mutableLoading.value = false
            }
        }
    }

    private fun refreshMoviesList() {
        _mutableLoading.value = true
        _mutableNetworkError.value = false
        viewModelScope.launch {
            try {
                repository.refreshPopularMovies(pageNum++)
            } catch (exception: IOException) {
                _mutableNetworkError.value = true
                Log.e("${this::class.simpleName} refreshMoviesList", "Catch exception: $exception")
            } finally {
                _mutableLoading.value = false
            }
        }
    }

    fun loadNextPage() {
        loadPageNum(pageNum++)
    }

    fun loadPageNum(page: Int) {
        _mutableLoading.value = true
        _mutableNetworkError.value = false
        viewModelScope.launch {
            try {
                repository.loadPopularMovies(page)
            } catch (exception: IOException) {
                _mutableNetworkError.value = true
                Log.e("${this::class.simpleName} loadPageNum", "Catch exception: $exception")
            } finally {
                _mutableLoading.value = false
            }
        }
    }
}