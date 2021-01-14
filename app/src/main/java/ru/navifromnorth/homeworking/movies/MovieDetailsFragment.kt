package ru.navifromnorth.homeworking.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.actors.ActorsListAdapter
import ru.navifromnorth.homeworking.data.Movie

class MovieDetailsFragment : Fragment() {

    private var recycler: RecyclerView? = null
    private var movie: Movie? = null

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(MOVIE_OBJECT, movie)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie = savedInstanceState?.getParcelable(MOVIE_OBJECT)
            ?: arguments?.getParcelable(MOVIE_OBJECT) ?: return

        setButtons(view)
        setContent(view, movie)
    }

    private fun setContent(view: View, movie: Movie?) {
        view.findViewById<TextView>(R.id.PG).text =
            context?.getString(R.string.PG_text_view, movie?.minimumAge)
        view.findViewById<TextView>(R.id.FilmTitle).text = movie?.title
        view.findViewById<RatingBar>(R.id.Rating).rating = movie?.ratings?.div(2) ?: 0.0.toFloat()
        view.findViewById<TextView>(R.id.SumReviews).text =
            context?.getString(R.string.reviews_text_view, movie?.numberOfRatings)
        movie?.overview?.let { view.findViewById<TextView>(R.id.StorylineDetails).text = it }
        view.findViewById<TextView>(R.id.tags).text =
            movie?.genres?.joinToString(separator = ", ", transform = { item -> item.name })
        scope.launch {
            val pic = Glide.with(view.context).load(movie?.backdrop)
            launch(Dispatchers.Main) { pic.into(view.findViewById(R.id.HeaderImage)) }
        }

        recycler = view.findViewById(R.id.CastRV)
        recycler?.layoutManager = LinearLayoutManager(
            this.context, LinearLayoutManager.HORIZONTAL, false
        )
        recycler?.adapter = movie?.actors?.let { ActorsListAdapter(it, view.context) }
    }

    private fun setButtons(view: View) {
        view.findViewById<TextView>(R.id.BackButton).setOnClickListener {
            val lastFragment: Fragment? = fragmentManager?.fragments?.last()
            fragmentManager?.beginTransaction()?.apply {
                lastFragment?.let {
                    remove(it)
                    commit()
                }
            }
            lastFragment?.let { fragmentManager?.popBackStack() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        movie = null
        recycler?.adapter = null
        recycler = null
    }

    companion object {
        val MOVIE_OBJECT: String = Movie::class.java.name

        fun newInstance(movie: Movie): MovieDetailsFragment {
            val args = Bundle()
            args.putParcelable(MOVIE_OBJECT, movie)
            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}