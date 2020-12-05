package ru.navifromnorth.homeworking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), MoviesListFragment.TransactionsFragmentClicks,
    MovieDetailsFragment.BackButtonListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.MovieListFragment, MoviesListFragment())
            .commit()
    }

    override fun openMovie1() {
        supportFragmentManager.beginTransaction().apply {
            addToBackStack(null)
            add(R.id.MovieDetailsFragment, MovieDetailsFragment())
            commit()
        }
    }

    override fun RemoveThisFragment() {
        val lastFragment: Fragment = supportFragmentManager.fragments.last()
        supportFragmentManager.beginTransaction().apply {
            remove(lastFragment)
            commit()
        }
        supportFragmentManager.popBackStack()
    }
}