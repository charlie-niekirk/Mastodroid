package me.cniekirk.mastodroid.feature.feed.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import me.cniekirk.domain.PostActionsUseCase
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.repository.AuthenticationRepository
import me.cniekirk.mastodroid.core.data.repository.StatusRepository
import me.cniekirk.mastodroid.core.model.AuthStatus.LOGGED_IN
import me.cniekirk.mastodroid.core.model.AuthStatus.NO_TOKEN
import me.cniekirk.mastodroid.core.model.AuthStatus.TOKEN_EXPIRED
import me.cniekirk.mastodroid.core.model.PostAction
import me.cniekirk.mastodroid.core.model.UserFeedItem
import me.cniekirk.mastodroid.feature.feed.R
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
internal class FeedViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val statusRepository: StatusRepository,
    private val postActionsUseCase: PostActionsUseCase
) : ViewModel(), ContainerHost<FeedState, FeedEffect> {

    override val container = container<FeedState, FeedEffect>(
        FeedState(feedItems = statusRepository.getStatusFeed().cachedIn(viewModelScope))
    ) {
        // Check if logged in
        checkAuthStatus()
    }

    private fun checkAuthStatus() = intent {
        // Stub for now
        when (val authCheckResponse = authenticationRepository.getAuthStatus()) {
            is Result.Failure -> {
                postSideEffect(FeedEffect.Error(R.string.auth_status_error))
            }
            is Result.Success -> {
                when (authCheckResponse.data) {
                    LOGGED_IN -> {
                        reduce { state.copy(viewState = ViewState.SUCCESS) }
                    }
                    TOKEN_EXPIRED, NO_TOKEN -> {
                        reduce { state.copy(viewState = ViewState.AUTH_ERROR) }
                    }
                }
            }
        }
    }

    fun onItemClicked(feedItem: UserFeedItem) = intent {
        postSideEffect(FeedEffect.ItemClicked(feedItem))

        reduce {
            state.copy(
                postState = state.postState.copy(
                    post = feedItem
                )
            )
        }

        // Get comments
        getStatusContext(feedItem.id.toString())
    }

    fun onBackPressed() = intent {
        reduce {
            state.copy(
                postState = PostState()
            )
        }

        postSideEffect(FeedEffect.BackPressed)
    }

    private fun getStatusContext(postId: String) = intent {
        when (val statusAndContext = statusRepository.getStatusContext(postId)) {
            is Result.Failure -> {
                // Post error
                reduce { state.copy(postState = state.postState.copy(areCommentsLoading = false)) }
                postSideEffect(FeedEffect.Error(R.string.post_load_error))
            }
            is Result.Success -> {
                // Reduce state
                reduce {
                    state.copy(
                        postState = state.postState.copy(
                            areCommentsLoading = false,
                            comments = statusAndContext.data
                        )
                    )
                }
            }
        }
    }

    fun favouritePost(id: String) = intent {
        when (postActionsUseCase.invoke(PostAction.Favourite(id))) {
            is Result.Failure -> {
                postSideEffect(FeedEffect.Error(R.string.post_action_error))
            }
            is Result.Success -> {
                // TODO: Do the animation for the like button
//                reduce {
//                    state.copy(
//
//                    )
//                }
            }
        }
    }

    fun sharePost(id: String) = intent {
        reduce {
            state.copy(
                postState = state.postState.copy(
                    bottomShareSheet = BottomShareSheet(
                        isVisible = true,
                        postId = id
                    )
                ),
            )
        }
    }

    fun shareLink() = intent {
        val id = state.postState.bottomShareSheet.postId
        if (id?.equals(state.postState.post?.id.toString(), true) == true) {
            postSideEffect(FeedEffect.ShareLink(state.postState.post?.url ?: ""))
        } else {
            // Try and find in comments
        }
    }

    fun shareMedia() = intent {
        val id = state.postState.bottomShareSheet.postId
        if (id?.equals(state.postState.post?.id.toString(), true) == true) {
            val media = state.postState.post?.mediaInfo
            postSideEffect(FeedEffect.ShareMedia(media?.map { it.url } ?: listOf()))
        } else {
            // Try and find in comments
        }
    }

    fun onDismissShare() = intent {
        reduce {
            state.copy(
                postState = state.postState.copy(
                    bottomShareSheet = state.postState.bottomShareSheet.copy(
                        isVisible = false
                    )
                ),
            )
        }
    }
}