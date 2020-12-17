package ru.navifromnorth.homeworking

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
import ru.navifromnorth.homeworking.data.models.Movie


class MovieDetailsFragment(private val movie: Movie) : Fragment() {

    private var recycler: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        movie.initializeDetails()

        view.findViewById<TextView>(R.id.PG).text =
            context?.getString(R.string.PG_text_view, movie.PG)
        view.findViewById<TextView>(R.id.FilmTitle).setText(movie.titleId)
        view.findViewById<RatingBar>(R.id.Rating).rating = movie.rating.toFloat()
        view.findViewById<TextView>(R.id.SumReviews).text =
            context?.getString(R.string.reviews_text_view, movie.countReviews)
        movie.storylineId?.let { view.findViewById<TextView>(R.id.StorylineDetails).setText(it) }
        view.findViewById<TextView>(R.id.tags).text = movie.tags.joinToString(separator = ", ",
            transform = { item -> view.context.getString(item) })
        movie.backgroundPosterId?.let {
            view.findViewById<ImageView>(R.id.HeaderImage).setImageResource(it)
        }

        recycler = view.findViewById(R.id.CastRV)
        recycler?.layoutManager = LinearLayoutManager(
            this.context, LinearLayoutManager.HORIZONTAL, false
        )
        recycler?.adapter = movie.cast?.let { ActorsListAdapter(it, view.context) }
    }

    override fun onDetach() {
        super.onDetach()
        recycler = null
    }

    companion object {
        fun newInstance(movie: Movie) = MovieDetailsFragment(movie)
    }
}