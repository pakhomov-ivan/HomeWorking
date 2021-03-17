package ru.navifromnorth.homeworking

interface Router {
    fun openMoviesList(addToBackStack: Boolean = true)
    fun openMovieDetails(movieId: Long)
    fun showProgressBar(isVisible: Boolean = false)
}