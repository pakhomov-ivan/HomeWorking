package ru.navifromnorth.homeworking.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.Router
import ru.navifromnorth.homeworking.data.Movie
import ru.navifromnorth.homeworking.list.viewmodel.MovieListVMFactory
import ru.navifromnorth.homeworking.list.viewmodel.MovieListViewModel
import ru.navifromnorth.homeworking.list.viewmodel.Status

class MoviesListFragment : Fragment() {

    private val parentRouter: Router? get() = activity as? Router

    //this will initialize only after onAttach!
    private val viewModel: MovieListViewModel by activityViewModels { MovieListVMFactory() }

    //recycler
    private var recycler: RecyclerView? = null
    private var moviesAdapter: MoviesAdapter? = null

    private var progressBar: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //sets
        initViews(view)
        setUpRecycler(view)

        //subscribe on some events
        viewModel.status.observe(viewLifecycleOwner, this::showStatus)
        viewModel.moviesList.observe(viewLifecycleOwner, this::updateAdapter)

        if (savedInstanceState == null)
            viewModel.loadNextPage()
    }

    private fun showStatus(status: Status) {
        when (status) {
            is Status.Success -> progressBar?.visibility = View.GONE
            is Status.Loading -> progressBar?.visibility = View.VISIBLE
            is Status.Error.InnerError -> parentRouter?.showError("Ахтунг! Внутренняя ошибка!")
            is Status.Error.NetworkError -> parentRouter?.showError("Конец Света - нет Интернета!!!")
            is Status.Error.UnexpectedError -> parentRouter?.showError("Fatal Error")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        recycler?.adapter = null
        moviesAdapter = null
        recycler = null

        progressBar = null
    }

    private fun updateAdapter(movies: List<Movie>) {
        moviesAdapter?.bindMovies(movies)
    }

    private fun initViews(view: View) {
        recycler = view.findViewById(R.id.rv_movies)

        progressBar = view.findViewById(R.id.loading_progressbar)
    }

    private fun setUpRecycler(view: View) {
        moviesAdapter =
            MoviesAdapter(this::showMovieDetails, viewModel::onLikeClick, viewModel::loadNextPage)

        recycler?.layoutManager = GridLayoutManager(
            activity,
            view.context.resources.getInteger(R.integer.spanCount),
            GridLayoutManager.VERTICAL,
            false
        )
        recycler?.adapter = moviesAdapter
    }

    private fun showMovieDetails(movieId: Int) {
        parentRouter?.openMovieDetails(movieId)
    }

    companion object {
        fun newInstance() = MoviesListFragment()
    }
}
