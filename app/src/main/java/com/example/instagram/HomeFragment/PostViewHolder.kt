package com.example.instagram.HomeFragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.UserPostsModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.post_item_layout.view.*

class PostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun setData(userPostsModel: UserPostsModel) {

        var publisherImage = ""

        var uID = ""
        firebaseDatabase.getReference("posts").orderByChild("postId")
            .equalTo(userPostsModel.postId)
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        uID = snapshot.children.toString()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })

        firebaseDatabase.getReference("users").child(uID).child("profileImage")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    publisherImage = snapshot.value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        view.apply {
            Glide.with(user_profile_image_search).load(publisherImage).into(user_profile_image_search)
            user_name_search.text = userPostsModel.publisher
            Glide.with(post_image_home).load(userPostsModel.image).into(post_image_home)
            publisher.text = userPostsModel.publisher
            description.text = userPostsModel.description
        }
    }

}