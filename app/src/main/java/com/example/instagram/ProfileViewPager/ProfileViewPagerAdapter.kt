package com.example.instagram.ProfileViewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instagram.Fragments.ProfileFragment

class ProfileViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
     return  2
    }

    override fun createFragment(position: Int): Fragment {

            when (position) {
                1 -> return TagPostFragment()
            }
            return ProfileFragment()
    }
}

