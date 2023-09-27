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
    val userName: String,
    val userHandle: String,
    val userProfilePictureUrl: String,
    val timeSincePost: String,
    val content: String,
    val numComments: Int,
    val mediaInfo: ImmutableList<MediaInfo>
)
