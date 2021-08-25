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
import kotlinx.android.synthetic.main.single_video_row.view.*

class VideoAdapter(var videoList: List<videoModel>): RecyclerView.Adapter<VideoAdapter.myviewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_video_row,
            parent,
            false
        )
        return myviewholder(view)

    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {

        val video = videoList[position]
        holder.setData(video)

    }

    override fun getItemCount(): Int {
    return    videoList.size
    }



    class myviewholder(val view: View): RecyclerView.ViewHolder(view) {
       lateinit var videoProgressBar: ProgressBar
        fun setData(videomodel: videoModel) {
            view.apply {
                textUserName.text = videomodel.Usernames
                textDescription.text = videomodel.description
                VideoView2.setVideoPath(videomodel.url)
                textUserName.setEllipsize(TextUtils.TruncateAt.MARQUEE)
                textUserName.setSelected(true)
                textDescription.setEllipsize(TextUtils.TruncateAt.MARQUEE)
                textDescription.setSelected(true)



                VideoView2.setOnPreparedListener(OnPreparedListener { mediaPlayer ->
                    videoProgressBar.setVisibility(View.INVISIBLE)
                    mediaPlayer.start()
                })

                VideoView2.setOnCompletionListener(OnCompletionListener { mediaPlayer -> mediaPlayer.start() })
            }

        }
    }


}