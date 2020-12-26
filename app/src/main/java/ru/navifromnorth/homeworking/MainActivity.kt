package ru.navifromnorth.homeworking

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.navifromnorth.homeworking.data.models.Movie
import ru.navifromnorth.homeworking.data.models.MoviesDataSource

class MainActivity : AppCompatActivity() {

    private val movies: List<Movie> = MoviesDataSource.getPreviewMovies()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.MovieListFragment, MoviesListFragment.newInstance(movies))
            .commit()
    }
}