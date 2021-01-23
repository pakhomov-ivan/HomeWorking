package ru.navifromnorth.homeworking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.navifromnorth.homeworking.data.Movie

class MovieListViewModel(private val moviesRepository: MoviesRepository, private val router: Router?) : ViewModel() {

    private val _mutableMoviesList = MutableLiveData<List<Movie>>()
//    private val _mutableSelectedMovie = MutableLiveData<Movie>()

    val moviesList: LiveData<List<Movie>> get() = _mutableMoviesList
//    val selectedMovie: LiveData<Movie> get() = _mutableSelectedMovie

    fun updateMoviesList() {
        viewModelScope.launch {
            _mutableMoviesList.value = moviesRepository.getMovies()
        }
    }

    fun onMovieSelected(movie: Movie?) {
//        _mutableSelectedMovie.value = movie
        router?.openMovieDetails(movie)
    }

    fun onLikeClick(id: Int?){
        _mutableMoviesList.value?.apply {
            this.first{ it.id == id }.apply { hasLike = hasLike.not() }
        }
    }
}