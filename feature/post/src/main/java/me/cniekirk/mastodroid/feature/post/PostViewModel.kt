package me.cniekirk.mastodroid.feature.post

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.cniekirk.domain.PostActionsUseCase
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.data.repository.StatusRepository
import me.cniekirk.mastodroid.core.model.PostAction
import me.cniekirk.mastodroid.feature.post.navigation.PostArgs
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val statusRepository: StatusRepository,
    private val postActionsUseCase: PostActionsUseCase
) : ViewModel(), ContainerHost<PostState, PostEffect> {

    private val postArgs = PostArgs(savedStateHandle)

    override val container = container<PostState, PostEffect>(PostState()) {
        loadPostData(postArgs.postId)
    }

    private fun loadPostData(postId: String) = intent {
        when (val statusAndContext = statusRepository.getStatus(postId)) {
            is Result.Failure -> {
                // Post error
                reduce { state.copy(isLoading = false) }
                postSideEffect(PostEffect.Error(R.string.post_load_error))
            }
            is Result.Success -> {
                // Reduce state
                reduce {
                    state.copy(
                        isLoading = false,
                        post = statusAndContext.data
                    )
                }

                // Then get the comments
                getStatusContext(postId)
            }
        }
    }

    private fun getStatusContext(postId: String) = intent {
        when (val statusAndContext = statusRepository.getStatusContext(postId)) {
            is Result.Failure -> {
                // Post error
                reduce { state.copy(areCommentsLoading = false) }
                postSideEffect(PostEffect.Error(R.string.post_load_error))
            }
            is Result.Success -> {
                // Reduce state
                reduce {
                    state.copy(
                        areCommentsLoading = false,
                        comments = statusAndContext.data
                    )
                }
            }
        }
    }

    fun favouritePost(id: String) = intent {
        when (postActionsUseCase.invoke(PostAction.Favourite(id))) {
            is Result.Failure -> {
                postSideEffect(PostEffect.Error(R.string.post_action_error))
            }
            is Result.Success -> {
                reduce {
                    state.copy(
                        comments = state.comments
                    )
                }
            }
        }
    }

    fun reblogPost(id: String) = intent {
        when (postActionsUseCase.invoke(PostAction.Reblog(id))) {
            is Result.Failure -> {
                postSideEffect(PostEffect.Error(R.string.post_action_error))
            }
            is Result.Success -> {
                reduce {
                    state.copy(
                        comments = state.comments
                    )
                }
            }
        }
    }

    fun replyPost(id: String) = intent {
        postSideEffect(PostEffect.ReplyToPost(id))
    }

    fun sharePost(id: String) = intent {
        reduce {
            state.copy(
                bottomShareSheet = BottomShareSheet(
                    isVisible = true,
                    postId = id
                )
            )
        }
    }

    fun shareLink() = intent {
        val id = state.bottomShareSheet.postId
        if (id?.equals(state.post?.id.toString(), true) == true) {
            postSideEffect(PostEffect.ShareLink(state.post?.url ?: ""))
        } else {
            // Try and find in comments
        }
    }

    fun shareMedia() = intent {
        val id = state.bottomShareSheet.postId
        if (id?.equals(state.post?.id.toString(), true) == true) {
            val media = state.post?.mediaInfo
            postSideEffect(PostEffect.ShareMedia(media?.map { it.url } ?: listOf()))
        } else {
            // Try and find in comments
        }
    }

    fun onDismissShare() = intent {
        reduce {
            state.copy(
                bottomShareSheet = state.bottomShareSheet.copy(
                    isVisible = false
                )
            )
        }
    }
}