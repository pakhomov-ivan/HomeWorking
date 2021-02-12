package ru.navifromnorth.homeworking

interface Router {
    fun openMoviesList(addToBackStack: Boolean = true)
    fun openMovieDetails(movieId: Int)
    fun showProgressBar(isVisible: Boolean = false)
    fun showError(message: String)
}