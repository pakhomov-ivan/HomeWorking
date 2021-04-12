package ru.navifromnorth.homeworking.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.list.viewmodel.MovieListVMFactory
import ru.navifromnorth.homeworking.list.viewmodel.MovieListViewModel

class MoviesListFragment : Fragment() {

    //this will initialize only after onAttach!
    private val viewModel: MovieListViewModel by viewModels {
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
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //sets
        initViews(view)
        setUpRecycler(view)

        //subscribe on some events
        viewModel.isLoading.observe(viewLifecycleOwner, isLoading)
        viewModel.isNetworkError.observe(viewLifecycleOwner, showNetworkError)
        viewModel.moviesList.observe(viewLifecycleOwner, Observer { moviesAdapter?.submitList(it) })
    }

    private val isLoading = Observer<Boolean> { isLoading ->
        when (isLoading) {
            true -> progressBar?.visibility = View.VISIBLE
            false -> progressBar?.visibility = View.GONE
        }
    }

    private val showNetworkError = Observer<Boolean> { isError ->
        if (isError)
            Toast.makeText(context, "Service is not available", Toast.LENGTH_LONG).show()
        //todo: what else?
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
        moviesAdapter = MoviesAdapter(this::showMovieDetails) { movieId, hasLike ->
            viewModel.onLikeClick(
                movieId,
                hasLike
            )
        }

        recycler?.layoutManager = GridLayoutManager(
            activity,
            view.context.resources.getInteger(R.integer.spanCount),
            GridLayoutManager.VERTICAL,
            false
        )
        recycler?.adapter = moviesAdapter
    }

    private fun showMovieDetails(movieId: Long) {
        findNavController().navigate(
            MoviesListFragmentDirections.actionMoviesListFragment2ToMovieDetailsFragment(movieId)
        )
    }

    companion object {
        fun newInstance() = MoviesListFragment()
    }
}
