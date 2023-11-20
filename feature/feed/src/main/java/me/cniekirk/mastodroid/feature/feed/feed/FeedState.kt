package me.cniekirk.mastodroid.feature.feed.feed

import androidx.annotation.StringRes
import androidx.paging.PagingData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.cniekirk.mastodroid.core.model.UserFeedItem

enum class ViewState {
    LOADING,
    SUCCESS,
    AUTH_ERROR
}

data class BottomShareSheet(
    val isVisible: Boolean = false,
    val postId: String? = null
)

data class PostState(
    val areCommentsLoading: Boolean = true,
    val bottomShareSheet: BottomShareSheet = BottomShareSheet(),
    val post: UserFeedItem? = null,
    val comments: ImmutableList<UserFeedItem> = persistentListOf()
)

data class FeedState(
    val viewState: ViewState = ViewState.LOADING,
    val feedItems: Flow<PagingData<UserFeedItem>> = flowOf(),
    val postState: PostState = PostState()
)

sealed class FeedEffect {

    data class Error(@StringRes val message: Int) : FeedEffect()

    data class ItemClicked(val feedItem: UserFeedItem) : FeedEffect()

    data class ReplyToPost(val postId: String) : FeedEffect()

    data class ShareMedia(val mediaUrls: List<String>) : FeedEffect()

    data class ShareLink(val link: String) : FeedEffect()

    data object BackPressed : FeedEffect()
}