package com.example.instagram.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagram.R
import com.example.instagram.UserDetailsModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private var listImage = ArrayList<String>()
    private var listUser = ArrayList<UserDetailsModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setImageData()
        setRecyclerView(0)

        ivSearch.setOnClickListener {
            setUserData()
            setRecyclerView(1)
            ivBack.visibility = VISIBLE
        }

        ivBack.setOnClickListener {
            ivBack.visibility = GONE
            setRecyclerView(0)
        }

    }

    private fun setUserData() {
        for (i in 0..19)
            listUser.add(UserDetailsModel())
    }

    private fun setImageData() {
        for (i in 0..99)
        listImage.add("Random")
    }

    private fun setRecyclerView(viewId: Int) {
        rvRecyclerView_Search.adapter = SearchFragmentAdapter(listImage,listUser,viewId)
        if(viewId == 0)
           rvRecyclerView_Search.layoutManager = StaggeredGridLayoutManager(3,VERTICAL)
        else
           rvRecyclerView_Search.layoutManager = LinearLayoutManager(context)
    }

}