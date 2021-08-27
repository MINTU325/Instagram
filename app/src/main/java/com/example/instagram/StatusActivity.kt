package com.example.instagram

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class StatusActivity : AppCompatActivity() {
    var video: VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        video = findViewById(R.id.vlVideo) as VideoView
        video!!.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.statusvideo))
        video!!.requestFocus()
        video!!.start()
        video!!.setOnCompletionListener {

            video!!.setVideoURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/my-project-firebase-b3527.appspot.com/o/new%2FOriginal%20Audio.mp4?alt=media&token=e572efdf-c61e-4868-93ef-5eddfdecdf6e"))
            video!!.start()
        }
    }
}