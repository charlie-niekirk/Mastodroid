package me.cniekirk.mastodroid.core.data.mapper

import me.cniekirk.mastodroid.core.model.UserFeedItem
import me.cniekirk.mastodroid.core.network.model.NetworkStatus

fun NetworkStatus.toUserFeedItem(): UserFeedItem {
    return UserFeedItem(
        id = this.id?.toLong() ?: 0L,
        userName = this.account?.displayName ?: "",
        userHandle = this.account?.username ?: "",
        userProfilePictureUrl = this.account?.avatar ?: "",
        timeSincePost = this.createdAt ?: "",
        content = this.content ?: "",
        numComments = this.repliesCount ?: 0,
//        mediaUrl = this.mediaAttachments
    )
}