package com.example.instagram.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.NotificationFragment.NotificationFragmentAdapter
import com.example.instagram.R
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        var list = ArrayList<String>()
        for(i in 0..9)
            list.add("Random")
        rvRecyclerViewNotify.adapter = NotificationFragmentAdapter(list)
        rvRecyclerViewNotify.layoutManager = LinearLayoutManager(context)
    }

}