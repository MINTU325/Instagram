package com.example.instagram.HomeFragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.Models.ListsPassingHelper
import com.example.instagram.Models.UserPostsModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.post_item_layout.view.*

class PostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun setData(userPostsModel: UserPostsModel) {

        var publisherImage = ""

        for(i in ListsPassingHelper.userDetailsList){
            if (i.username == userPostsModel.publisher){
                publisherImage = i.profileImage.toString()
                break
            }
        }

        view.apply {
            Glide.with(user_profile_image_search).load(publisherImage).into(user_profile_image_search)
            user_name_search.text = userPostsModel.publisher
            Glide.with(post_image_home).load(userPostsModel.image).into(post_image_home)
            publisher.text = userPostsModel.publisher
            description.text = userPostsModel.description
        }
    }

}