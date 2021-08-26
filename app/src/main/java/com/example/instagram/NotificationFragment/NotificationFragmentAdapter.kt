package com.example.instagram.NotificationFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R

class NotificationFragmentAdapter(private var list : List<String>) : RecyclerView.Adapter<NotificationFragmentViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_notification_layout_view,parent,false)
        return NotificationFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationFragmentViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}