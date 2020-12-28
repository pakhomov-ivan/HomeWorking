package ru.navifromnorth.homeworking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.navifromnorth.homeworking.movies.MoviesListFragment
import ru.navifromnorth.homeworking.data.models.Movie
import ru.navifromnorth.homeworking.data.models.MoviesDataSource

class MainActivity : AppCompatActivity() {

    private val movies: List<Movie> = MoviesDataSource.getPreviewMovies()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = if (supportFragmentManager.fragments.size > 0)
            supportFragmentManager.fragments.last()
        else
            MoviesListFragment.newInstance(movies)

        supportFragmentManager.beginTransaction()
            .replace(R.id.MovieListFragment, fragment)
            .commit()
    }
}