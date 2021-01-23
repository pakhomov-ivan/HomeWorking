package ru.navifromnorth.homeworking

import ru.navifromnorth.homeworking.data.Movie

interface Router {
    fun openMoviesList()
    fun openMovieDetails(movie: Movie?)
}