package com.mindease.kipsigis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindease.kipsigis.databinding.ItemVideoBinding

class VideoAdapter(
    private val videos: List<Video>,
    private val onClick: (Video) -> Unit
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemVideoBinding)
        : RecyclerView.ViewHolder(binding.root)

    private fun getYoutubeThumbnail(url: String): String {
        val id = when {
            url.contains("youtu.be") ->
                url.substringAfter("youtu.be/").substringBefore("?")

            url.contains("watch?v=") ->
                url.substringAfter("watch?v=").substringBefore("&")

            else -> ""
        }
        return "https://img.youtube.com/vi/$id/0.jpg"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videos[position]

        holder.binding.txtTitle.text = video.title
        holder.binding.txtDescription.text = video.description

        Glide.with(holder.itemView.context)
            .load(getYoutubeThumbnail(video.url))
            .placeholder(R.drawable.placeholder_video)
            .into(holder.binding.imgThumbnail)

        holder.binding.root.setOnClickListener { onClick(video) }
    }

    override fun getItemCount() = videos.size
}
