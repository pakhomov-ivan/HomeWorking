package ru.navifromnorth.homeworking

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.navifromnorth.homeworking.list.MovieDetailsEvents
import ru.navifromnorth.homeworking.list.MovieDetailsFragment
import ru.navifromnorth.homeworking.list.MoviesListFragment

class MainActivity : AppCompatActivity(), Router, MovieDetailsEvents {

    private var framelayout: FrameLayout? = null
    private var progressbar: View? = null
    private var errorMessage: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: openMoviesList(false)

        setViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        framelayout = null
        progressbar = null
        errorMessage = null
    }

    private fun setViews() {
        progressbar = findViewById(R.id.activity_progressbar)
        framelayout = findViewById(R.id.MovieListFragment)
        errorMessage = findViewById(R.id.error_message)
    }

    override fun openMoviesList(addToBackStack: Boolean) =
        openFragment(MoviesListFragment.newInstance(), addToBackStack)

    override fun openMovieDetails(movieId: Int) {
        openFragment(MovieDetailsFragment.newInstance(movieId))
    }

    override fun showProgressBar(isVisible: Boolean) {
        when (isVisible) {
            true -> {
                framelayout?.visibility = View.GONE
                progressbar?.visibility = View.VISIBLE
            }
            false -> {
                framelayout?.visibility = View.VISIBLE
                progressbar?.visibility = View.GONE
            }
        }
    }

    override fun showError(message: String) {
        openFragment(ErrorFragment.newInstance(message), false)
    }

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