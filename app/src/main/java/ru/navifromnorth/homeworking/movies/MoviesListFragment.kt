package ru.navifromnorth.homeworking.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.MovieListVMFactory
import ru.navifromnorth.homeworking.MovieListViewModel
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.Router
import ru.navifromnorth.homeworking.data.Movie

class MoviesListFragment : Fragment() {

    private val parentRouter: Router? get() = activity as? Router

    private val viewModel: MovieListViewModel by activityViewModels { //this will initialize only after onAttach!
        MovieListVMFactory(
            requireContext()
        )
    }

    private var recycler: RecyclerView? = null
    private var moviesAdapter: MoviesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setUpRecycler(view)

        viewModel.moviesList.observe(viewLifecycleOwner, this::updateAdapter)
        viewModel.moviesList.value ?: viewModel.updateMoviesList()
    }

    override fun onDestroy() {
        super.onDestroy()
        recycler?.adapter = null
        moviesAdapter = null
        recycler = null
    }

    private fun updateAdapter(movies: List<Movie>) {
        moviesAdapter?.bindMovies(movies)
    }

    private fun initViews(view: View) {
        recycler = view.findViewById(R.id.rv_movies)
    }

    private fun setUpRecycler(view: View) {
        moviesAdapter = MoviesAdapter(this::showMovieDetails, viewModel::onLikeClick)

        recycler?.layoutManager = GridLayoutManager(
            activity,
            view.context.resources.getInteger(R.integer.spanCount),
            GridLayoutManager.VERTICAL,
            false
        )
        recycler?.adapter = moviesAdapter
    }

    private fun showMovieDetails(movie: Movie?){
        parentRouter?.openMovieDetails(movie)
    }

    companion object {
        fun newInstance() = MoviesListFragment()
    }
}
