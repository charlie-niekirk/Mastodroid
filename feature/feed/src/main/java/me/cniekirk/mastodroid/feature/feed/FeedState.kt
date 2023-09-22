package me.cniekirk.mastodroid.feature.feed

enum class ViewState {
    LOADING,
    SUCCESS,
    AUTH_ERROR
}

data class FeedState(
    val viewState: ViewState = ViewState.LOADING,
)

sealed class FeedEffect {


}