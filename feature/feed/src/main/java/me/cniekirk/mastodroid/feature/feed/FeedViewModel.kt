package me.cniekirk.mastodroid.feature.feed

import android.text.Spanned
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import me.cniekirk.domain.PostActionsUseCase
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.mapper.toUserFeedItem
import me.cniekirk.mastodroid.core.data.repository.AuthenticationRepository
import me.cniekirk.mastodroid.core.data.repository.HomeFeedPagingSource
import me.cniekirk.mastodroid.core.data.repository.StatusRepository
import me.cniekirk.mastodroid.core.model.AuthStatus.LOGGED_IN
import me.cniekirk.mastodroid.core.model.AuthStatus.NO_TOKEN
import me.cniekirk.mastodroid.core.model.AuthStatus.TOKEN_EXPIRED
import me.cniekirk.mastodroid.core.model.PostAction
import me.cniekirk.mastodroid.core.ui.toAnnotatedString
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

//
//    fun reblogPost(id: String) = intent {
//        when (postActionsUseCase.invoke(PostAction.Reblog(id))) {
//            is Result.Failure -> {
//                postSideEffect(PostEffect.Error(R.string.post_action_error))
//            }
//            is Result.Success -> {
//                reduce {
//                    state.copy(
//                        comments = state.comments
//                    )
//                }
//            }
//        }
//    }
//
//    fun replyPost(id: String) = intent {
//        postSideEffect(PostEffect.ReplyToPost(id))
//    }
//
//    fun sharePost(id: String) = intent {
//        reduce {
//            state.copy(
//                bottomShareSheet = BottomShareSheet(
//                    isVisible = true,
//                    postId = id
//                )
//            )
//        }
//    }
//
//    fun shareLink() = intent {
//        val id = state.bottomShareSheet.postId
//        if (id?.equals(state.post?.id.toString(), true) == true) {
//            postSideEffect(PostEffect.ShareLink(state.post?.url ?: ""))
//        } else {
//            // Try and find in comments
//        }
//    }
//
//    fun shareMedia() = intent {
//        val id = state.bottomShareSheet.postId
//        if (id?.equals(state.post?.id.toString(), true) == true) {
//            val media = state.post?.mediaInfo
//            postSideEffect(PostEffect.ShareMedia(media?.map { it.url } ?: listOf()))
//        } else {
//            // Try and find in comments
//        }
//    }
//
//    fun onDismissShare() = intent {
//        reduce {
//            state.copy(
//                bottomShareSheet = state.bottomShareSheet.copy(
//                    isVisible = false
//                )
//            )
//        }
//    }
}