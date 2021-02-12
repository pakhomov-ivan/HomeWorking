package ru.navifromnorth.homeworking.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.data.Video

class VideosListAdapter : RecyclerView.Adapter<SimplePreviewVideoViewHolder>() {

    private var videosList: List<Video> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimplePreviewVideoViewHolder = SimplePreviewVideoViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.simple_video_view_holder, parent, false)
    )

    override fun onBindViewHolder(holder: SimplePreviewVideoViewHolder, position: Int) {
        holder.onBind(videosList[position])
    }

    override fun getItemCount(): Int = videosList.size

    fun bindVideos(videosList: List<Video>?) {
        this.videosList = videosList ?: emptyList()
        notifyDataSetChanged()
    }
}