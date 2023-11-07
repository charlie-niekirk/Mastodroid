package me.cniekirk.mastodroid.feature.post

import androidx.annotation.StringRes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import me.cniekirk.mastodroid.core.model.UserFeedItem

data class BottomShareSheet(
    val isVisible: Boolean = false,
    val postId: String? = null
)

data class PostState(
    val isLoading: Boolean = true,
    val areCommentsLoading: Boolean = true,
    val bottomShareSheet: BottomShareSheet = BottomShareSheet(),
    val post: UserFeedItem? = null,
    val comments: ImmutableList<UserFeedItem> = persistentListOf()
)

sealed class PostEffect {

    data class Error(@StringRes val message: Int) : PostEffect()

    data class ReplyToPost(val postId: String) : PostEffect()

    data class ShareMedia(val mediaUrls: List<String>) : PostEffect()

    data class ShareLink(val link: String) : PostEffect()
}