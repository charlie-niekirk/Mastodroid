package me.cniekirk.mastodroid.core.model

data class UserFeedItem(
    val id: Long,
    val userName: String,
    val userHandle: String,
    val userProfilePictureUrl: String,
    val timeSincePost: String,
    val content: String,
    val numComments: Int,
//    val mediaUrl: String
)
