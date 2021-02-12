package ru.navifromnorth.homeworking.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.Router
import ru.navifromnorth.homeworking.data.MovieDetails
import ru.navifromnorth.homeworking.data.Video
import ru.navifromnorth.homeworking.details.viewmodel.MovieDetailsViewModel
import ru.navifromnorth.homeworking.details.viewmodel.MovieDetailsViewModelFactory
import ru.navifromnorth.homeworking.list.viewmodel.Status
import ru.navifromnorth.homeworking.videos.VideosListAdapter

class MovieDetailsFragment : Fragment() {

    //viewModel
    private val viewModel: MovieDetailsViewModel by activityViewModels { MovieDetailsViewModelFactory() }

    //activityRouter
    private val parentRouter: Router? get() = activity as? Router

    private var videosListRecycler: RecyclerView? = null
    private var videsListAdapter: VideosListAdapter? = null
    private var movie: MovieDetails? = null
    private var eventsListener: MovieDetailsEvents? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners(view)
        setVideosListRecycler(view)

        //subscribe on events
        viewModel.selectedMovie.observe(viewLifecycleOwner, this::setContent)
        viewModel.status.observe(viewLifecycleOwner, this::showStatus)

        //get MovieDetails
        arguments?.getInt(MOVIE_DETAILS_ID)?.let {
            viewModel.getMovieDetails(it)
        }
    }

    private fun setVideosListRecycler(view: View) {
        videosListRecycler = view.findViewById(R.id.VideosRV)
        videosListRecycler?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        videsListAdapter = VideosListAdapter()
        videosListRecycler?.adapter = videsListAdapter
    }

    private fun showStatus(status: Status) {
        when (status) {
            is Status.Loading -> visibilityProgressbar(true)
            is Status.Success -> visibilityProgressbar(false)
            is Status.Error.InnerError -> parentRouter?.showError("Ахтунг! Внутренняя ошибка!")
            is Status.Error.NetworkError -> parentRouter?.showError("Конец Света - нет Интернета!!!")
            is Status.Error.UnexpectedError -> parentRouter?.showError("Fatal Error")
        }
    }

    private fun visibilityProgressbar(isShow: Boolean) = parentRouter?.showProgressBar(isShow)

    private fun setContent(movie: MovieDetails) {
        view?.findViewById<TextView>(R.id.PG)?.text =
            context?.getString(R.string.PG_text_view, if (movie.adult) 18 else 0)

        view?.findViewById<TextView>(R.id.FilmTitle)?.text = movie.title
        view?.findViewById<RatingBar>(R.id.Rating)?.rating = movie.vote_average.div(2)
        view?.findViewById<TextView>(R.id.SumReviews)?.text =
            context?.getString(R.string.reviews_text_view, movie.vote_count)

        view?.findViewById<TextView>(R.id.StorylineDetails)?.text = movie.overview
        view?.findViewById<TextView>(R.id.tags)?.text =
            movie.genres.joinToString(separator = ", ", transform = { item -> item.name })

        view?.findViewById<ImageView>(R.id.HeaderImage)?.load(movie.backdrop)

        videsListAdapter?.bindVideos(movie.video)
    }

    private fun setListeners(view: View) {
        eventsListener = context as? MovieDetailsEvents
        view.findViewById<TextView>(R.id.BackButton)
            .setOnClickListener { eventsListener?.onBackButtonClick() }
    }

    override fun onDestroy() {
        super.onDestroy()
        movie = null
        videosListRecycler?.adapter = null
        videsListAdapter = null
        videosListRecycler = null
    }

    companion object {
        private val MOVIE_DETAILS_ID: String = MovieDetails::class.java.name + "_ID"

        fun newInstance(movieId: Int): MovieDetailsFragment {
            val args = Bundle()
            args.putInt(MOVIE_DETAILS_ID, movieId)
            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

interface MovieDetailsEvents {
    fun onBackButtonClick()
}