package com.example.instagram.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.UserDetailsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private var listImage = ArrayList<String>()
    private var listUser = ArrayList<UserDetailsModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setImageData()
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivSearch.setOnClickListener {
            listUser.clear()
            setUserData(etSearch.text.toString())
            setRecyclerView(1)
            ivBack.visibility = VISIBLE
        }

        ivBack.setOnClickListener {
            ivBack.visibility = GONE
            setRecyclerView(0)
        }

    }

    override fun onStart() {
        super.onStart()
            setRecyclerView(0)
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

    private fun setImageData() {
        FirebaseStorage.getInstance().getReference("User Posts")
            .listAll().addOnSuccessListener { listResult ->
                for(fileRef in listResult.items){
                    fileRef.downloadUrl.addOnSuccessListener { uri ->
                        listImage.add(uri.toString())
                    }
                }
            }
    }

    private fun setRecyclerView(viewId: Int) {
        rvRecyclerView_Search.adapter = SearchFragmentAdapter(listImage,listUser,viewId)
        if(viewId == 0)
           rvRecyclerView_Search?.layoutManager = GridLayoutManager(context,3)
        else
           rvRecyclerView_Search?.layoutManager = LinearLayoutManager(context)
    }

}