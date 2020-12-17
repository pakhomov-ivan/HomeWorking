package ru.navifromnorth.homeworking

import android.os.Bundle
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
    private var movies: List<Movie> = MoviesDataSource.getPreviewMovies()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movies.forEach{
            it.hasLike = savedInstanceState?.getBoolean(it.titleId.toString()) ?: it.hasLike
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.rv_movies)
        recycler?.layoutManager =
            GridLayoutManager(
                view.context,
                view.resources.getInteger(R.integer.spanCount),
                GridLayoutManager.VERTICAL,
                false
            )
        recycler?.adapter = MoviesAdapter(clickListener, onLikeCallback, view.context)
    }

    override fun onStart() {
        super.onStart()
        (recycler?.adapter as? MoviesAdapter)?.apply {
            bindMovies(movies)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        movies.forEach{
            outState.putBoolean(it.titleId.toString(), it.hasLike)
        }
    }

    override fun onDetach() {
        super.onDetach()
        recycler = null
    }

    private val clickListener = object : ClickListener {
        override fun onMovieClick(movie: Movie) {
            fragmentManager?.beginTransaction()?.apply {
                addToBackStack(null)
                add(R.id.MovieListFragment, MovieDetailsFragment.newInstance(movie))
                commit()
            }
        }
    }

    private val onLikeCallback = object : ClickOnLikeCallback{
        override fun onLikeClick(_movies: List<Movie>) {
            movies = _movies
        }
    }

    companion object {
        fun newInstance() = MoviesListFragment()
    }
}
