package ru.navifromnorth.homeworking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.navifromnorth.homeworking.data.Movie
import ru.navifromnorth.homeworking.movies.MovieDetailsEvents
import ru.navifromnorth.homeworking.movies.MovieDetailsFragment
import ru.navifromnorth.homeworking.movies.MoviesListFragment

class MainActivity : AppCompatActivity(), Router, MovieDetailsEvents {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: openMoviesList(false)
    }

    override fun openMoviesList(addToBackStack: Boolean) =
        openFragment(MoviesListFragment.newInstance(), addToBackStack)

    override fun openMovieDetails(movie: Movie) =
        openFragment(MovieDetailsFragment.newInstance(movie))

    private fun openFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.MovieListFragment, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(fragment::class.java.name)
        }

        transaction.commit()
    }

    override fun onBackButtonClick() {
        if (supportFragmentManager.fragments.size > 0)
            supportFragmentManager.popBackStack()
        openMoviesList(addToBackStack = false)
    }
}