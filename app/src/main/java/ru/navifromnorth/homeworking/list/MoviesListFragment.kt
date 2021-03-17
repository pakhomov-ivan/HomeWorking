package ru.navifromnorth.homeworking.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.Router
import ru.navifromnorth.homeworking.list.viewmodel.MovieListVMFactory
import ru.navifromnorth.homeworking.list.viewmodel.MovieListViewModel

class MoviesListFragment : Fragment() {

    private val parentRouter: Router? get() = activity as? Router

    //this will initialize only after onAttach!
    private val viewModel: MovieListViewModel by activityViewModels {
        MovieListVMFactory(requireActivity().application)
    }

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
        viewModel.isLoading.observe(viewLifecycleOwner, this::showLoading)
        viewModel.isNetworkError.observe(viewLifecycleOwner, this::showNetworkError)
        viewModel.moviesList.observe(viewLifecycleOwner, { moviesAdapter?.submitList(it) })
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> progressBar?.visibility = View.VISIBLE
            false -> progressBar?.visibility = View.GONE
        }
    }

    private fun showNetworkError(isError: Boolean) {
        when (isError) {
            true -> Toast.makeText(context, "Service is not available", Toast.LENGTH_LONG).show()
            false -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        recycler?.adapter = null
        moviesAdapter = null
        recycler = null

        progressBar = null
    }

    private fun initViews(view: View) {
        progressBar = view.findViewById(R.id.loading_progressbar)
    }

    private fun setUpRecycler(view: View) {
        recycler = view.findViewById(R.id.rv_movies)
        moviesAdapter = MoviesAdapter(this::showMovieDetails, viewModel::onLikeClick)

        recycler?.layoutManager = GridLayoutManager(
            activity,
            view.context.resources.getInteger(R.integer.spanCount),
            GridLayoutManager.VERTICAL,
            false
        )
        recycler?.adapter = moviesAdapter
    }

    private fun showMovieDetails(movieId: Long) {
        parentRouter?.openMovieDetails(movieId)
    }

    companion object {
        fun newInstance() = MoviesListFragment()
    }
}
