package ru.navifromnorth.homeworking.actors

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.Actor

class ActorPreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.ActorNameTextView)
    private val avatar: ImageView = itemView.findViewById(R.id.ActorImageView)
    private val scope = CoroutineScope(Dispatchers.IO)

    fun onBind(actor: Actor){
        name.text = actor.name
//        avatar.setImageResource(actor.avatarId)
        scope.launch {
            val pic = Glide.with(itemView.context).load(actor.picture)
            launch(Dispatchers.Main){ pic.into(avatar) }
        }
    }
}