package com.example.instagram.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.instagram.ProfileViewPager.ProfileViewPagerAdapter
import com.example.instagram.R
import com.google.android.material.tabs.TabLayout
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
//    private lateinit var ViewPageerr: ViewPager2
//    private lateinit var tabLayout: TabLayout
//    private lateinit var pagerAdapter: ProfileViewPagerAdapter

    private var ImageUri3: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myprofile_image.setOnClickListener {
            val intent = CropImage.activity(ImageUri3)
                .getIntent(requireContext())
            startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)

//            val fm :FragmentManager = childFragmentManager
//            pagerAdapter = ProfileViewPagerAdapter(fm, lifecycle)
//            ViewPageerr.setAdapter(pagerAdapter)
//
//            tabLayout.addTab(tabLayout.newTab().setText("MY Posts"))
//            tabLayout.addTab(tabLayout.newTab().setText("Tag Posts"))
//            tabLayout.addOnTabSelectedListener(
//                object : OnTabSelectedListener {
//                    override fun onTabSelected(tab: TabLayout.Tab) {
//                        ViewPageerr.setCurrentItem(tab.position)
//                    }
//
//                    override fun onTabUnselected(tab: TabLayout.Tab) {}
//                    override fun onTabReselected(tab: TabLayout.Tab) {}
//                })

        }
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(5).jpg?alt=media&token=9df2e006-6335-457c-b4bc-17610d64de64")
            .into(imageIV1)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(1).jpg?alt=media&token=69295414-e475-420a-8bf4-77dd27b4109a")
            .into(imageIV2)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(2).jpg?alt=media&token=c7249f98-0a2b-4392-96c0-382b59acf2a1")
            .into(imageIV3)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(3).jpg?alt=media&token=86023396-5b74-486a-a38c-49891f881de2")
            .into(imageIV4)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(4).jpg?alt=media&token=85abd3e8-21c8-4129-be7e-403e9b9dd51a")
            .into(imageIV5)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fdownload%20(1).jpg?alt=media&token=04165875-380a-4796-9a9a-8c5044582251")
            .into(imageIV6)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages.jpg?alt=media&token=15ca848c-b09c-4189-b721-2e864b3f91b8")
            .into(imageIV7)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val result2 = CropImage.getActivityResult(data)
            ImageUri3 = result2.uri
            myprofile_image.setImageURI(ImageUri3)
        }
    }
}

