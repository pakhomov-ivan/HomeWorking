package ru.navifromnorth.homeworking.actors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.Actor

class ActorsListAdapter(private val actors: List<Actor>, context: Context) :
    RecyclerView.Adapter<ActorPreviewViewHolder>() {

    private var layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorPreviewViewHolder {
        return ActorPreviewViewHolder(
            layoutInflater
                .inflate(R.layout.actor_view_holder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorPreviewViewHolder, position: Int) {
        holder.onBind(actors.elementAt(position))
    }

    override fun getItemCount(): Int = actors.size
}