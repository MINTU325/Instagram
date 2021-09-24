package com.example.instagram.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.MOdels.ListsPassingHelper
import com.example.instagram.R
import com.example.instagram.MOdels.UserDetailsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var listImage = ListsPassingHelper.postImagesUrl
    private var listUser = ListsPassingHelper.userDetailsList


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

                setRecyclerView(0)

        etSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                ivBack.visibility = VISIBLE
                listUser = ArrayList<UserDetailsModel>()
                for(i in ListsPassingHelper.userDetailsList){
                    if(i.fullName?.substring(0,s.toString().length).equals(s.toString(),ignoreCase = true))
                    {
                        listUser.add(i)
                    }
                }
                setRecyclerView(1)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        ivBack.setOnClickListener {
            ivBack.visibility = GONE
            setRecyclerView(0)
        }

    }


    private fun setUserData(search : String) {
        FirebaseDatabase.getInstance().getReference("users")
            .orderByChild("fullName").startAt(search.equals(search,ignoreCase = true))
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(snapshots in snapshot.children){
                        listUser.add(snapshots.getValue(UserDetailsModel::class.java)!!)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun setRecyclerView(viewId: Int) {
        rvRecyclerView_Search?.adapter = SearchFragmentAdapter(listImage,listUser,viewId)
        if(viewId == 0)
           rvRecyclerView_Search?.layoutManager = GridLayoutManager(context,3)
        else
           rvRecyclerView_Search?.layoutManager = LinearLayoutManager(context)
    }

}