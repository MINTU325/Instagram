package com.example.instagram.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.instagram.R
import com.example.instagram.Reels.VideoAdapter
import com.example.instagram.Reels.videoModel
import kotlinx.android.synthetic.main.fragment_reels.*
import java.util.*

class ReelsFragment : Fragment() {

    var videoList = mutableListOf<videoModel>();
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reels, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildDta()

    }

    private fun buildDta() {
        videoList.add(
            videoModel(
                "MintuSaini34",
                "# reels, #CoolPics",
                "https://firebasestorage.googleapis.com/v0/b/my-project-firebase-b3527.appspot.com/o/new%2FLost%20Without%20You.mp4?alt=media&token=50ca4f03-13d4-40cd-b4c0-167a575e6989"
            )
        )
        videoList.add(
            videoModel(
                "Manish",
                "# reels, #CoolPics",
                "https://firebasestorage.googleapis.com/v0/b/my-project-firebase-b3527.appspot.com/o/new%2FOriginal%20Audio.mp4?alt=media&token=e572efdf-c61e-4868-93ef-5eddfdecdf6e"
            )
        )
        videoList.add(
            videoModel(
                "Rahul.23",
                "# reels, #CoolPics",
                "https://firebasestorage.googleapis.com/v0/b/my-project-firebase-b3527.appspot.com/o/new%2FSenorita.mp4?alt=media&token=9c637724-934a-4df2-8e51-a401d5577f39"
            )
        )
        videoList.add(
            videoModel(
                "Vicky",
                "# reels, #CoolPics",
                "https://firebasestorage.googleapis.com/v0/b/my-project-firebase-b3527.appspot.com/o/tiktok%20videos%2FInstagram.mp4?alt=media&token=23e27c21-a85b-4e1c-bc67-3979778b016d"
            )
        )
        videoList.add(
            videoModel(
                "Abhishek",
                "# reels, #CoolPics",
                "https://firebasestorage.googleapis.com/v0/b/skype-8a506.appspot.com/o/yt1s.com%20-%20Tu%20chale%20sang%20chale%20song%20whatsapp%20status%20%20nature%20whatsapp%20status_360p.mp4?alt=media&token=c0ac13ef-4254-4327-9281-bb6baa332809"
            )
        )
        viewpager2.adapter = VideoAdapter(videoList)
    }

}