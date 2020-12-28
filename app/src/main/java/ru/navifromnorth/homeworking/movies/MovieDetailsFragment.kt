package ru.navifromnorth.homeworking.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.actors.ActorsListAdapter
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.models.Movie

class MovieDetailsFragment : Fragment() {

    private var recycler: RecyclerView? = null
    private var movie: Movie? = null

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

        movie?.initializeDetails()
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

        view.findViewById<TextView>(R.id.PG).text =
            context?.getString(R.string.PG_text_view, movie?.PG)
        view.findViewById<TextView>(R.id.FilmTitle).setText(movie?.titleId ?: -1)
        view.findViewById<RatingBar>(R.id.Rating).rating = movie?.rating ?: 0.0.toFloat()
        view.findViewById<TextView>(R.id.SumReviews).text =
            context?.getString(R.string.reviews_text_view, movie?.countReviews)
        movie?.storylineId?.let {
            view.findViewById<TextView>(R.id.StorylineDetails).setText(it)
        }
        view.findViewById<TextView>(R.id.tags).text =
            movie?.tags?.joinToString(separator = ", ",
                transform = { item -> view.context.getString(item) })
        movie?.backgroundPosterId?.let {
            view.findViewById<ImageView>(R.id.HeaderImage).setImageResource(it)
        }

        recycler = view.findViewById(R.id.CastRV)
        recycler?.layoutManager = LinearLayoutManager(
            this.context, LinearLayoutManager.HORIZONTAL, false
        )
        recycler?.adapter = movie?.cast?.let { ActorsListAdapter(it, view.context) }
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