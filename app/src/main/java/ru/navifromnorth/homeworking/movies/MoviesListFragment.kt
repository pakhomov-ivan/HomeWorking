package ru.navifromnorth.homeworking.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.Movie
import ru.navifromnorth.homeworking.data.loadMovies

class MoviesListFragment : Fragment() {

    private var recycler: RecyclerView? = null
    private var movies: List<Movie> = listOf()
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArray(MOVIES_LIST, movies.toTypedArray())
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movies = savedInstanceState?.getParcelableArray(MOVIES_LIST)?.map { it as Movie }?.toList()
            ?: listOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.rv_movies)
        recycler?.layoutManager =
            GridLayoutManager(
                activity,
                view.resources.getInteger(R.integer.spanCount),
                GridLayoutManager.VERTICAL,
                false
            )
        recycler?.adapter = MoviesAdapter(clickListener, view.context)
    }

    override fun onStart() {
        super.onStart()
        (recycler?.adapter as? MoviesAdapter)?.apply {
            if (movies.isNotEmpty()) bindMovies(movies)
            else
                scope.launch {
                    movies = context?.let { loadMovies(it) } ?: listOf()
                    bindMovies(movies)
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recycler?.adapter = null
        recycler = null
//        movies = null
        scope.cancel()
    }

    private val clickListener = object : ClickListener {
        override fun onMovieClick(movie: Movie?) {
            if (movie != null)
                fragmentManager?.beginTransaction()?.apply {
                    addToBackStack(null)
                    add(R.id.MovieListFragment, MovieDetailsFragment.newInstance(movie))
                    commit()
                }
        }

        override fun onLikeClick(movie: Movie?) {
            movies.first { it == movie }
                .apply { this.hasLike = this.hasLike.not() }
        }
    }

    companion object {
        private const val MOVIES_LIST = "MoviesList"

        fun newInstance() = MoviesListFragment()

//        fun newInstance(moviesIn: List<Movie>): MoviesListFragment {
//            val args = Bundle()
//            args.putParcelableArray(MOVIES_LIST, moviesIn.toTypedArray())
//            val fragment = MoviesListFragment()
//            fragment.arguments = args
//            return fragment
//        }
    }
}
