package me.cniekirk.mastodroid.feature.feed

import androidx.annotation.StringRes
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.cniekirk.mastodroid.core.model.UserFeedItem

enum class ViewState {
    LOADING,
    SUCCESS,
    AUTH_ERROR
}

data class FeedState(
    val viewState: ViewState = ViewState.LOADING,
    val feedItems: Flow<PagingData<UserFeedItem>> = flowOf()
)

sealed class FeedEffect {

    data class Error(@StringRes val message: Int) : FeedEffect()
}