package com.example.instagram.Fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.NotificationFragment.NotificationFragmentAdapter
import com.example.instagram.R
import com.example.instagram.MOdels.UserPostsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private var list = ArrayList<UserPostsModel>()
    private val databaseReference = FirebaseDatabase.getInstance().getReference("posts")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (uid in snapshot.children) {
                    for (postId in uid.children) {
                        var userPostsModel: UserPostsModel =
                            postId.getValue(UserPostsModel::class.java)!!
                        addToList(userPostsModel)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addToList(userPostsModel: UserPostsModel) {
        list.add(userPostsModel)
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed(Runnable {
            setRecyclerView()
        },1000)
    }

    private fun setRecyclerView() {
        list.reverse()
        rvRecyclerViewNotify?.adapter = NotificationFragmentAdapter(list)
        rvRecyclerViewNotify?.layoutManager = LinearLayoutManager(context)
    }

}