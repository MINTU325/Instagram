package com.example.instagram.NotificationFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.UserPostsModel

class NotificationFragmentAdapter(private var list : List<UserPostsModel>) : RecyclerView.Adapter<NotificationFragmentViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_notification_layout_view,parent,false)
        return NotificationFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationFragmentViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}