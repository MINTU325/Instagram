package com.example.instagram.HomeFragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.Models.ListsPassingHelper
import com.example.instagram.Models.UserPostsModel
import com.example.instagram.databinding.PostItemLayoutBinding
import com.google.firebase.database.FirebaseDatabase

class PostViewHolder(val binding: PostItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun setData(userPostsModel: UserPostsModel) {
        var publisherImage = ""

        for (i in ListsPassingHelper.userDetailsList) {
            if (i.username == userPostsModel.publisher) {
                publisherImage = i.profileImage.toString()
                break
            }
        }

        // Using ViewBinding to set data
        binding.apply {
            Glide.with(userProfileImageSearch).load(publisherImage).into(userProfileImageSearch)
            userNameSearch.text = userPostsModel.publisher
            Glide.with(postImageHome).load(userPostsModel.image).into(postImageHome)
            publisher.text = userPostsModel.publisher
            description.text = userPostsModel.description
        }
    }
}
