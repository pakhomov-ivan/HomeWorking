package ru.navifromnorth.homeworking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.navifromnorth.homeworking.movies.MoviesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = if (supportFragmentManager.fragments.size > 0)
            supportFragmentManager.fragments.last()
        else
            MoviesListFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .replace(R.id.MovieListFragment, fragment)
            .commit()
    }
}