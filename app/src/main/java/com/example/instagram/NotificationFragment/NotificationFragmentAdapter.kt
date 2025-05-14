package com.example.instagram.NotificationFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.FragmentNotificationLayoutViewBinding
import com.example.instagram.Models.UserPostsModel

class NotificationFragmentAdapter(private var list : List<UserPostsModel>) : RecyclerView.Adapter<NotificationFragmentViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationFragmentViewHolder {
        val binding = FragmentNotificationLayoutViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationFragmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationFragmentViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
