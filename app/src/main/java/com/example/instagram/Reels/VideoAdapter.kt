package com.example.instagram.Reels

import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.databinding.SingleVideoRowBinding

class VideoAdapter(var videoList: List<videoModel>): RecyclerView.Adapter<VideoAdapter.myviewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        // Inflate the view binding
        val binding = SingleVideoRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return myviewholder(binding)
    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        val video = videoList[position]
        holder.setData(video)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    class myviewholder(private val binding: SingleVideoRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun setData(videomodel: videoModel) {
            binding.apply {
                // Set data using the binding class
                textUserName.text = videomodel.Usernames
                textDescription.text = videomodel.description
                VideoView2.setVideoPath(videomodel.url)
                textUserName.setEllipsize(TextUtils.TruncateAt.MARQUEE)
                textUserName.isSelected = true
                textDescription.setEllipsize(TextUtils.TruncateAt.MARQUEE)
                textDescription.isSelected = true

                // Set listeners on the VideoView
                VideoView2.setOnPreparedListener(OnPreparedListener { mediaPlayer ->
                    videoProgressBar.visibility = View.INVISIBLE
                    mediaPlayer.start()
                })

                VideoView2.setOnCompletionListener(OnCompletionListener { mediaPlayer ->
                    mediaPlayer.start() // Optionally loop the video
                })
            }
        }
    }
}
