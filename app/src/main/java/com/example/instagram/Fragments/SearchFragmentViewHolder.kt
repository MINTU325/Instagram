package com.example.instagram.Fragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.UserDetailsModel
import kotlinx.android.synthetic.main.fragment_search_layout_view_accounts.view.*
import kotlinx.android.synthetic.main.fragment_search_layout_view_random.view.*

class SearchFragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setRandomData(imageUrl : String){
        Glide.with(itemView.ivRandom).load(imageUrl).into(itemView.ivRandom)
    }

    fun setAccountsData(userDetailsModel: UserDetailsModel){
        itemView.apply {
            userDetailsModel.apply {
                Glide.with(civImage).load(profileImage).into(civImage)
                tvUsername.text = username
                tvFullName.text = fullName
            }
        }
    }
}