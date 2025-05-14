package com.example.instagram.NotificationFragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.Models.ListsPassingHelper
import com.example.instagram.Models.UserPostsModel
import com.example.instagram.databinding.FragmentNotificationLayoutViewBinding

class NotificationFragmentViewHolder(private val binding: FragmentNotificationLayoutViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun setData(userPostsModel: UserPostsModel) {
        var publisherImage = ""

        // Getting the publisher's image from the user details list
        for (i in ListsPassingHelper.userDetailsList) {
            if (i.username == userPostsModel.publisher) {
                publisherImage = i.profileImage.toString()
                break
            }
        }

        // Using ViewBinding to set data
        binding.apply {
            Glide.with(civImage).load(publisherImage).into(civImage)
            tvUsername.text = userPostsModel.publisher
            tvPostDesc.text = userPostsModel.description
        }
    }
}
