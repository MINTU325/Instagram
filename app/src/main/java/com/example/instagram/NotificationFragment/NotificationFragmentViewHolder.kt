package com.example.instagram.NotificationFragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.UserPostsModel
import kotlinx.android.synthetic.main.fragment_notification_layout_view.view.*

class NotificationFragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setData(userPostsModel: UserPostsModel){
        itemView.apply {
            userPostsModel.apply {
                tvUsername.text = publisher
                tvPostDesc.text = description
            }
        }
    }

}