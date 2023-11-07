package me.cniekirk.mastodroid.core.model

import kotlinx.collections.immutable.ImmutableList

enum class MediaType {
    IMAGE,
    VIDEO
}

data class MediaInfo(
    val mediaType: MediaType,
    val ratio: Float,
    val url: String
)

data class UserFeedItem(
    val id: Long,
    val url: String,
    val userName: String,
    val userHandle: String,
    val userProfilePictureUrl: String,
    val timeSincePost: String,
    val timeString: String,
    val content: String,
    val numComments: Int,
    val numReblogs: Int,
    val numFavourites: Int,
    val client: String,
    val isFavourited: Boolean,
    val isReblogged: Boolean,
    val mediaInfo: ImmutableList<MediaInfo>,
    val replyToUser: String = "",
    val rebloggedPost: UserFeedItem? = null
)
