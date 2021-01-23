package ru.navifromnorth.homeworking

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.navifromnorth.homeworking.data.Movie
import ru.navifromnorth.homeworking.movies.MovieDetailsFragment
import ru.navifromnorth.homeworking.movies.MoviesListFragment

class MainActivity : AppCompatActivity(), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: openMoviesList()
    }

    override fun openMoviesList() = openFragment(MoviesListFragment.newInstance()/*this is problem */)

    override fun openMovieDetails(movie: Movie?) =
        openFragment(MovieDetailsFragment.newInstance(movie))

    private fun openFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.MovieListFragment, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(fragment::class.java.name)
        }

        transaction.commit()
    }
}