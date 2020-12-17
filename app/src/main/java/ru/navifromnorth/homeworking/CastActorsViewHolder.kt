package ru.navifromnorth.homeworking

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.data.models.Actor

class CastActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val actorAvatar: ImageView = itemView.findViewById(R.id.ActorImageView)
    private val actorName: TextView = itemView.findViewById(R.id.ActorNameTextView)

    fun onBind(actor: Actor){
        actorAvatar.setImageResource(actor.avatarId)
        actorName.setText(actor.nameId)
    }
}