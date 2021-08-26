package com.example.instagram.HomeFragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.UserPostsModel
import kotlinx.android.synthetic.main.post_item_layout.view.*

class PostViewHolder(val view :View):RecyclerView.ViewHolder(view) {

    fun setData(userPostsModel: UserPostsModel){
        view.apply {
            user_name_search.text = userPostsModel.publisher
            Glide.with(post_image_home).load(userPostsModel.image).into(post_image_home)
            publisher.text = userPostsModel.publisher
            description.text = userPostsModel.description
        }
    }

}