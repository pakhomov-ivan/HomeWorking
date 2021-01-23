package ru.navifromnorth.homeworking.actors

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.Actor

class CastActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val actorAvatar: ImageView = itemView.findViewById(R.id.ActorImageView)
    private val actorName: TextView = itemView.findViewById(R.id.ActorNameTextView)

//    fun onBind(actor: Actor){
//        actorAvatar.setImageResource(actor.picture)
//        actorName.setText(actor.nameId)
//    }
}