package ru.navifromnorth.homeworking

import ru.navifromnorth.homeworking.data.Movie

interface Router {
    fun openMoviesList(addToBackStack: Boolean = true)
    fun openMovieDetails(movie: Movie?)
}