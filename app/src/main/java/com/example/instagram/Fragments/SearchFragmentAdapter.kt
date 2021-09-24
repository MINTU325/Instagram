package com.example.instagram.Fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.MOdels.UserDetailsModel

class SearchFragmentAdapter(
    private val listImage: List<String>,
    private val listUser: List<UserDetailsModel>,
    private val viewId: Int
) :
    RecyclerView.Adapter<SearchFragmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFragmentViewHolder {
        return if (viewId == 0) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_search_layout_view_random, parent, false)
            SearchFragmentViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_search_layout_view_accounts, parent, false)
            SearchFragmentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: SearchFragmentViewHolder, position: Int) {
        if (viewId == 0)
            holder.setRandomData(listImage[position])
        else
            holder.setAccountsData(listUser[position])
    }

    override fun getItemCount(): Int {
        return if (viewId == 0)
            listImage.size
        else
            listUser.size
    }

}