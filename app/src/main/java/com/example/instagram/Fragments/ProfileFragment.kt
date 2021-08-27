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
import com.example.instagram.ProfileViewPager.ProfileViewPagerAdapter
import com.example.instagram.R
import com.google.android.material.tabs.TabLayout
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var ViewPageerr: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var pagerAdapter: ProfileViewPagerAdapter

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

