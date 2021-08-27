package com.example.instagram

class UserPostsModel(
    val postId: String?,
    val image: String?,
    val description: String?,
    val publisher: String?
) {

    constructor() : this(null,null,null,null) {

    }

}