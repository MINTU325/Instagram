package com.example.instagram.NotificationFragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.Models.ListsPassingHelper
import com.example.instagram.Models.UserPostsModel
import kotlinx.android.synthetic.main.fragment_notification_layout_view.view.*

class NotificationFragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setData(userPostsModel: UserPostsModel){

        var publisherImage = ""

        for(i in ListsPassingHelper.userDetailsList){
            if (i.username == userPostsModel.publisher){
                publisherImage = i.profileImage.toString()
                break
            }
        }

        itemView.apply {
            userPostsModel.apply {
                Glide.with(civImage).load(publisherImage).into(civImage)
                tvUsername.text = publisher
                tvPostDesc.text = description
            }
        }
    }

}