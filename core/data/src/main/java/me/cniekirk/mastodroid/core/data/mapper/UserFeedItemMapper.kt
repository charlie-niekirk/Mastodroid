package me.cniekirk.mastodroid.core.data.mapper

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import me.cniekirk.mastodroid.core.model.MediaInfo
import me.cniekirk.mastodroid.core.model.MediaType
import me.cniekirk.mastodroid.core.model.UserFeedItem
import me.cniekirk.mastodroid.core.network.model.NetworkStatus

fun NetworkStatus.toUserFeedItem(): UserFeedItem {

    val media = this.mediaAttachments?.map { media ->
        MediaInfo(
            MediaType.IMAGE,
            media.meta?.original?.aspect?.toFloat() ?: 0f,
            media.url ?: ""
        )
    }

    return UserFeedItem(
        id = this.id?.toLong() ?: 0L,
        userName = this.account?.displayName ?: "",
        userHandle = this.account?.username ?: "",
        userProfilePictureUrl = this.account?.avatar ?: "",
        timeSincePost = this.createdAt ?: "",
        content = this.content ?: "",
        numComments = this.repliesCount ?: 0,
        mediaInfo = media?.toImmutableList() ?: persistentListOf()
    )
}