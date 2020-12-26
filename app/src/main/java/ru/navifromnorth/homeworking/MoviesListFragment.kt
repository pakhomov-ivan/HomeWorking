package ru.navifromnorth.homeworking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.data.models.Movie
import ru.navifromnorth.homeworking.data.models.MoviesDataSource

class MoviesListFragment : Fragment() {

    private var recycler: RecyclerView? = null
    private var movies: List<Movie>? = null // MoviesDataSource.getPreviewMovies()

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArray(MOVIES_LIST, movies?.toTypedArray())
        Log.e("onSaveInstanceState", movies.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movies = savedInstanceState?.getParcelableArray(MOVIES_LIST)?.map{ it as Movie}?.toList()
        movies = movies ?: arguments?.getParcelableArray(MOVIES_LIST)?.map{ it as Movie}?.toList()
//        movies = movies ?: MoviesDataSource.getPreviewMovies()
        Log.e("onCreate", movies.toString())
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
            bindMovies(movies)
        }
    }

    override fun onDestroy() {
        recycler?.adapter = null
        recycler = null
//        movies = null
        super.onDestroy()
    }

    private val clickListener = object : ClickListener {
        override fun onMovieClick(movie: Movie) {
//            fragmentManager?.beginTransaction()?.apply {
//                addToBackStack(null)
//                add(R.id.MovieListFragment, MovieDetailsFragment.newInstance())
//                commit()
//            }
        }

        override fun onLikeClick(movie: Movie) {
            movies?.first { it == movie }?.hasLike?.not()
        }
    }

    companion object {
        private const val MOVIES_LIST = "MoviesList"

        fun newInstance(moviesIn: List<Movie>): MoviesListFragment{
            val args = Bundle()
            args.putParcelableArray(MOVIES_LIST, moviesIn.toTypedArray())
            val fragment = MoviesListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
