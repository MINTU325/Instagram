package com.example.instagram.Models

class UserDetailsModel(
    val fullName: String?,
    val username: String?,
    val email: String?,
    var profileImage: String?,
    val bio: String?,
    val uid: String?
) {

    constructor() : this(null, null, null, null, null, null)
}