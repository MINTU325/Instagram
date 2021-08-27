package com.example.instagram

class UserDetailsModel(
    val fullName: String?,
    val username: String?,
    val email: String?,
    val profileImage: String?,
    val bio: String?,
    val uId: String?
) {

    constructor() : this(null, null, null, null, null, null)
}