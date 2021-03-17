package ru.navifromnorth.homeworking.videos

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.Video

class SimplePreviewVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val videoName: TextView = itemView.findViewById(R.id.videoName)
    val videoType: TextView = itemView.findViewById(R.id.videoType)
    val videoLink: TextView = itemView.findViewById(R.id.videoLink)

    fun onBind(video: Video){
        videoName.text = video.name
        videoType.text = video.type
        videoLink.text = video.key
    }
}