package ru.navifromnorth.homeworking.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.MovieDetails
import ru.navifromnorth.homeworking.details.viewmodel.MovieDetailsViewModel
import ru.navifromnorth.homeworking.details.viewmodel.MovieDetailsViewModelFactory
import ru.navifromnorth.homeworking.videos.VideosListAdapter

class MovieDetailsFragment : Fragment() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    //viewModel
    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory(
            args.movieID,
            requireActivity().application
        )
    }

    private var loadingProgressBar: ContentLoadingProgressBar? = null
    private var content: ScrollView? = null
    private var backdrop: ImageView? = null
    private var PG: TextView? = null
    private var filmTitle: TextView? = null
    private var rating: RatingBar? = null
    private var sumReviews: TextView? = null
    private var overview: TextView? = null
    private var genres: TextView? = null
    private var backButton: TextView? = null
    private var likeButton: ImageView? = null

    private var isLike: Boolean = false

    private var videosListRecycler: RecyclerView? = null
    private var videosListAdapter: VideosListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContentElements(view)
        setListeners()
        setVideosListRecycler(view)

        //subscribe on events
        viewModel.selectedMovie.observe(viewLifecycleOwner, setContent)
        viewModel.isLoading.observe(viewLifecycleOwner, visibilityProgressbar)
    }

    private fun setContentElements(view: View) {
        loadingProgressBar = view.findViewById(R.id.MoviesDetailsLoadingProgressBar)
        content = view.findViewById(R.id.MovieDetailsContentScrollView)

        backdrop = view.findViewById(R.id.HeaderImage)
        PG = view.findViewById(R.id.PG)
        filmTitle = view.findViewById(R.id.FilmTitle)
        rating = view.findViewById(R.id.Rating)
        sumReviews = view.findViewById(R.id.SumReviews)
        overview = view.findViewById(R.id.StorylineDetails)
        genres = view.findViewById(R.id.tags)

        backButton = view.findViewById(R.id.BackButton)
        likeButton = view.findViewById(R.id.LikeButton)
    }

    private val setContent = Observer<MovieDetails> { movie ->
        PG?.text = context?.getString(R.string.PG_text_view, movie.adult)

        filmTitle?.text = movie.title
        rating?.rating = movie.vote_average.div(2)
        sumReviews?.text = context?.getString(R.string.reviews_text_view, movie.vote_count)

        overview?.text = movie.overview
        genres?.text =
            movie.genres.joinToString(separator = ", ", transform = { item -> item.name })

        backdrop?.load(movie.backdrop)

        setImageLikeButton(movie.like).also { isLike = movie.like }

        videosListAdapter?.bindVideos(movie.videos)
    }

    private fun setVideosListRecycler(view: View) {
        videosListRecycler = view.findViewById(R.id.VideosRV)
        videosListRecycler?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        videosListAdapter = VideosListAdapter()
        videosListRecycler?.adapter = videosListAdapter
    }

    private val visibilityProgressbar = Observer<Boolean>{isLoading ->
        if (isLoading){
            loadingProgressBar?.visibility = View.VISIBLE
            content?.visibility = View.GONE
        } else {
            loadingProgressBar?.visibility = View.GONE
            content?.visibility = View.VISIBLE
        }
    }

    private fun setListeners() {
        backButton?.setOnClickListener { findNavController().navigateUp() }

        likeButton?.setOnClickListener {
            viewModel.onLikeClick(
                movieId = requireArguments().getLong(MOVIE_DETAILS_ID),
                hasLike = isLike.not(),
                forceUpdate = false
            )
            setImageLikeButton(isLike.not())
        }
    }

    private fun setImageLikeButton(like: Boolean) =
        when (like) {
            true -> likeButton?.setImageResource(R.drawable.ic_like_enable)
            false -> likeButton?.setImageResource(R.drawable.ic_like)
        }

    override fun onDestroy() {
        super.onDestroy()
        videosListRecycler?.adapter = null
        videosListAdapter = null
        videosListRecycler = null

        backdrop = null
        PG = null
        filmTitle = null
        rating = null
        sumReviews = null
        overview = null
        genres = null
        backButton = null
    }

    companion object {
        private val MOVIE_DETAILS_ID: String = MovieDetails::class.java.name + "_ID"

        fun newInstance(movieId: Long): MovieDetailsFragment {
            val args = Bundle()
            args.putLong(MOVIE_DETAILS_ID, movieId)
            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}