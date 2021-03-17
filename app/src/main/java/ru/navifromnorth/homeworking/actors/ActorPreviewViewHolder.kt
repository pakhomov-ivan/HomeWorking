package ru.navifromnorth.homeworking.actors

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.Actor

class ActorPreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.ActorNameTextView)
    private val avatar: ImageView = itemView.findViewById(R.id.ActorImageView)

    fun onBind(actor: Actor) {
        name.text = actor.name
        avatar.load(actor.picture)
    }
}